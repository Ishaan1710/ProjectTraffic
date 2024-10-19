import { Component } from '@angular/core';
import { TrafficPredictionService } from '../../services/traffic-prediction.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { timeout } from 'rxjs';
import { traffic } from '../../models/traffic';

@Component({
  selector: 'app-traffic-prediction-form',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './traffic-prediction-form.component.html',
  styleUrl: './traffic-prediction-form.component.css'
})
export class TrafficPredictionFormComponent {
  date!: string;
  areaName!: string;
  roadName!: string;
  prediction!: number;

  constructor(private trafficPredictionService: TrafficPredictionService) {}

  onSubmit() {
    // Call the service to get the prediction
    console.log(this.date);
    console.log(this.areaName);
    console.log(this.roadName);

    // this.trafficPredictionService.predictCongestion(this.date, this.areaName, this.roadName)
    //   .subscribe(result => {
    //     this.prediction = result;
    //   }, error => {
    //     console.error('Error fetching prediction:', error);
    //   });
    var traffic: traffic = {
      date: this.date,
      areaName: this.areaName,
      roadName: this.roadName
    };
    this.trafficPredictionService.predictCongestion(traffic).subscribe(result => {
      this.prediction = result;
      console.log("Service msg: ",result);
    });

    console.log("Outside msg: ",this.prediction);

  }
}
