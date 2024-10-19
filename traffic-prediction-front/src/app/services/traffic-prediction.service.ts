import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { traffic } from '../models/traffic';

@Injectable({
  providedIn: 'root'
})
export class TrafficPredictionService {
  private apiUrl = 'http://localhost:8081/api/traffic/predict';  // Update this URL once backend is ready

  constructor(private http: HttpClient) {}

  predictCongestion(traffic: traffic): Observable<string> {
    var response = this.http.post<string>(this.apiUrl, traffic);
    return response;//this.http.post<string>(this.apiUrl, traffic);
  }
}
