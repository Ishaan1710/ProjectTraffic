# Import necessary libraries
import pandas as pd
import numpy as np

# Load the dataset
# Assuming you have already uploaded the dataset or accessed it from Kaggle
df = pd.read_csv('Banglore_traffic_Dataset.csv')
df = df.drop(['Average Speed','Public Transport Usage', 'Weather Conditions', 'Traffic Signal Compliance', 'Parking Usage', 'Pedestrian and Cyclist Count','Environmental Impact', 'Roadwork and Construction Activity'], axis=1)
# Take a look at the first few rows
df.head()

# Convert 'Date' column to datetime
df['Date'] = pd.to_datetime(df['Date'], format='%Y-%m-%d')

# Extract day of the week
df['DayOfWeek'] = df['Date'].dt.day_name()

# Check the updated dataframe
df.head()

# Group by 'DayOfWeek', 'Area Name', and 'Road/Intersection Name' and calculate the mean for traffic-related features
aggregated_data = df.groupby(['DayOfWeek', 'Area Name', 'Road/Intersection Name']).agg({
    'Traffic Volume': 'mean',
    'Travel Time Index': 'mean',
    'Road Capacity Utilization': 'mean',
    'Incident Reports': 'mean'
}).reset_index()

# Display the aggregated data
aggregated_data.head()

# Merge the aggregated data back into the original dataset
df = pd.merge(df, aggregated_data, on=['DayOfWeek', 'Area Name', 'Road/Intersection Name'], how='left', suffixes=('', '_agg'))

# Check the merged dataset
df.head()

# Fill missing values with historical averages or mean for numeric columns only
numeric_cols = df.select_dtypes(include=np.number).columns
df[numeric_cols] = df[numeric_cols].fillna(df[numeric_cols].mean())

# Check for any remaining missing values
df.isnull().sum()

import xgboost as xgb
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error

# Define feature columns (exclude target 'Congestion Level')
features = ['Traffic Volume', 'Travel Time Index', 'Road Capacity Utilization', 'Incident Reports']

# Prepare the data for training
X = df[features]
y = df['Congestion Level']

# Split the data into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Initialize and train the XGBoost model
model = xgb.XGBRegressor(objective='reg:squarederror', n_estimators=50, learning_rate=0.1)

model.fit(X_train, y_train)

# Make predictions
y_pred = model.predict(X_test)

# Evaluate the model
mse = mean_squared_error(y_test, y_pred)
print(f"Mean Squared Error: {mse}")

from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import LSTM, Dense
from sklearn.preprocessing import MinMaxScaler

# Scale the data
scaler = MinMaxScaler()
X_scaled = scaler.fit_transform(X)

# Reshape the input for LSTM [samples, time steps, features]
X_scaled = X_scaled.reshape((X_scaled.shape[0], 1, X_scaled.shape[1]))

# Define the LSTM model
model_lstm = Sequential()
model_lstm.add(LSTM(50, return_sequences=True, input_shape=(X_scaled.shape[1], X_scaled.shape[2])))
model_lstm.add(LSTM(50))
model_lstm.add(Dense(1))

# Compile the model
model_lstm.compile(optimizer='adam', loss='mse')

# Train the model
model_lstm.fit(X_scaled, y, epochs=10, batch_size=32, validation_split=0.2)

# Evaluate the model
y_pred_lstm = model_lstm.predict(X_scaled)


import joblib

# Save the model to a file
joblib.dump(model, 'xgboost_traffic_model.json')
model_lstm.save('lstm_traffic_model.h5')  # This saves the model in HDF5 format
joblib.dump(model_lstm, 'lstm_traffic_model2.h5')
# To load the model later
loaded_model = joblib.load('xgboost_traffic_model.json')
loaded_model_lstm = joblib.load('lstm_traffic_model2.h5')


def preprocess_input_xgboost(date, area_name, road_name, historical_aggregates):
    # Convert the date to the day of the week
    day_of_week = pd.to_datetime(date).day_name()

    # Fetch the historical averages from the pre-aggregated data
    row = historical_aggregates[(historical_aggregates['DayOfWeek'] == day_of_week) &
                                (historical_aggregates['Area Name'] == area_name) &
                                (historical_aggregates['Road/Intersection Name'] == road_name)]

    if row.empty:
        return None  # Handle case where no historical data is available
    else:
        return row.iloc[0].drop(['DayOfWeek', 'Area Name', 'Road/Intersection Name'])

# Example usage
#user_input_features = preprocess_input('2024-09-26', 'Indiranagar', '100 Feet Road', aggregated_data)
user_input_features = preprocess_input_xgboost('2024-10-10', 'Whitefield', 'Marathahalli Bridge', aggregated_data)


# Convert the input into a DataFrame (for prediction)
if user_input_features is not None:
    user_input_df = pd.DataFrame([user_input_features])
    #user_input_df.columns = ['Traffic Volume', 'Travel Time Index', 'Road Capacity Utilization', 'Incident Reports']
    prediction_xgboost = loaded_model.predict(user_input_df)
    print(f"Predicted Congestion Level: {prediction_xgboost}")
else:
    print("No historical data available for the given input.")


def preprocess_input_lstm(date, area_name, road_name, historical_aggregates, scaler):
    # Convert the date to the day of the week
    day_of_week = pd.to_datetime(date).day_name()

    # Fetch the historical averages for the given day, area, and road
    row = historical_aggregates[(historical_aggregates['DayOfWeek'] == day_of_week) &
                                (historical_aggregates['Area Name'] == area_name) &
                                (historical_aggregates['Road/Intersection Name'] == road_name)]

    if row.empty:
        return None  # Handle case where no historical data is available
    else:
        # Select only the numerical columns for scaling (features)
        features = ['Traffic Volume', 'Travel Time Index', 'Road Capacity Utilization', 'Incident Reports']
        selected_features = row[features].values  # Extract the values as a 2D array

        # Scale the features using the MinMaxScaler (2D array input)
        scaled_features = scaler.transform(selected_features.reshape(1, -1))  # Keep it 2D for scaling

        # Now reshape the scaled data into 3D for LSTM (samples, time steps, features)
        scaled_features = scaled_features.reshape((scaled_features.shape[0], 1, scaled_features.shape[1]))

        return scaled_features

# Example usage
#user_input_features = preprocess_input('2024-09-26', 'Indiranagar', '100 Feet Road', aggregated_data, scaler)
user_input_features = preprocess_input_lstm('2024-10-10', 'Whitefield', 'Marathahalli Bridge', aggregated_data,scaler)

# Make a prediction
if user_input_features is not None:
    prediction_lstm = loaded_model_lstm.predict(user_input_features)
    print(f"Predicted Congestion Level: {prediction_lstm}")
else:
    print("No historical data available for the given input.")
