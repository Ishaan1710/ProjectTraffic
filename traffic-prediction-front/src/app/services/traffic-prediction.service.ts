import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TrafficPredictionService {
  private apiUrl = 'http://localhost:8081/api/traffic/predict';  // Update this URL once backend is ready

  constructor(private http: HttpClient) {}

  predictCongestion(date: string, areaName: string, roadName: string): Observable<any> {
    const requestBody = { date, areaName, roadName };
    return this.http.post<any>(this.apiUrl, requestBody);
  }
}
