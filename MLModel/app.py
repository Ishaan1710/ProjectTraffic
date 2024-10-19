from flask import Flask, request, jsonify
import pandas as pd
import joblib
import tensorflow as tf
from sklearn.preprocessing import MinMaxScaler
import xgboost as xgb
from tensorflow.keras.losses import MeanSquaredError

import os
print("Current working directory:", os.getcwd())

# Initialize the Flask app
app = Flask(__name__)

# Load models and pre-aggregated historical data
lstm_model = tf.keras.models.load_model('MLModel/models/lstm_traffic_model.h5', custom_objects={'mse': MeanSquaredError()})
xgboost_model = joblib.load('MLModel/models/xgboost_traffic_model.json')  # Path to saved XGBoost model
historical_aggregates = pd.read_csv('MLModel/models/Banglore_traffic_Dataset.csv')  # Path to the dataset

# Drop unused columns from the dataset
historical_aggregates = historical_aggregates.drop(
    ['Average Speed', 'Public Transport Usage', 'Weather Conditions', 
     'Traffic Signal Compliance', 'Parking Usage', 'Pedestrian and Cyclist Count', 
     'Environmental Impact', 'Roadwork and Construction Activity'], axis=1)

# Convert 'Date' column to datetime and extract the day of the week
historical_aggregates['Date'] = pd.to_datetime(historical_aggregates['Date'], format='%Y-%m-%d')
historical_aggregates['DayOfWeek'] = historical_aggregates['Date'].dt.day_name()

# Group the dataset and calculate the mean for traffic-related features
aggregated_data = historical_aggregates.groupby(
    ['DayOfWeek', 'Area Name', 'Road/Intersection Name']
).agg({
    'Traffic Volume': 'mean',
    'Travel Time Index': 'mean',
    'Road Capacity Utilization': 'mean',
    'Incident Reports': 'mean'
}).reset_index()

# Load the scaler for LSTM model input
scaler = joblib.load('MLModel/models/scaler.pkl')  # Path to your scaler file

# Preprocessing function for XGBoost
def preprocess_input_xgboost(date, area_name, road_name, historical_aggregates):
    day_of_week = pd.to_datetime(date).day_name()
    row = historical_aggregates[(historical_aggregates['DayOfWeek'] == day_of_week) &
                                (historical_aggregates['Area Name'] == area_name) &
                                (historical_aggregates['Road/Intersection Name'] == road_name)]
    if row.empty:
        return None
    else:
        return row.iloc[0].drop(['DayOfWeek', 'Area Name', 'Road/Intersection Name'])

# Preprocessing function for LSTM
def preprocess_input_lstm(date, area_name, road_name, historical_aggregates, scaler):
    day_of_week = pd.to_datetime(date).day_name()
    row = historical_aggregates[(historical_aggregates['DayOfWeek'] == day_of_week) &
                                (historical_aggregates['Area Name'] == area_name) &
                                (historical_aggregates['Road/Intersection Name'] == road_name)]
    if row.empty:
        return None
    else:
        features = ['Traffic Volume', 'Travel Time Index', 'Road Capacity Utilization', 'Incident Reports']
        selected_features = row[features].values
        scaled_features = scaler.transform(selected_features.reshape(1, -1))
        scaled_features = scaled_features.reshape((scaled_features.shape[0], 1, scaled_features.shape[1]))
        return scaled_features

# Route for XGBoost prediction
@app.route('/predict_xgboost', methods=['POST'])
def predict_xgboost():
    try:
        data = request.get_json()
        date = data['date']
        area_name = data['area_name']
        road_name = data['road_name']

        # Preprocess the input
        user_input = preprocess_input_xgboost(date, area_name, road_name, aggregated_data)

        if user_input is not None:
            # Convert the input into a DataFrame
            user_input_df = pd.DataFrame([user_input])
            
            # Make a prediction
            prediction = xgboost_model.predict(user_input_df)
            
            # Convert the prediction to Python native type
            return jsonify({'predicted_congestion_level': float(prediction[0])})
        else:
            return jsonify({'error': 'No historical data available for the given input.'})
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# Route for LSTM prediction
@app.route('/predict_lstm', methods=['POST'])
def predict_lstm():
    try:
        data = request.get_json()
        date = data['date']
        area_name = data['area_name']
        road_name = data['road_name']

        # Preprocess the input
        user_input_features = preprocess_input_lstm(date, area_name, road_name, aggregated_data, scaler)
        if user_input_features is not None:
            prediction = lstm_model.predict(user_input_features)
            return jsonify({'predicted_congestion_level': float(prediction[0][0])})
        else:
            return jsonify({"error": "No historical data available for the given input."}), 400
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# Main route
@app.route('/')
def index():
    return "Traffic Prediction API is running!"

# Run the Flask app
if __name__ == '__main__':
    app.run(debug=True)
