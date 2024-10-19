import { NgModule } from '@angular/core';
import { TrafficPredictionFormComponent } from './components/traffic-prediction-form/traffic-prediction-form.component';
import { provideHttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

export const routes: Routes = [
    { path: 'predict', component: TrafficPredictionFormComponent },
    { path: '', redirectTo: '/predict', pathMatch: 'full' }
  ];

@NgModule({
  declarations: [
    // Ensure this is declared here
  ],
  imports: [
    BrowserModule,
    FormsModule,  // Required for two-way data binding with ngModel
    CommonModule,
    RouterModule.forRoot(routes),
    // TrafficPredictionFormComponent
  ],
  providers: [provideHttpClient()],
  bootstrap: []
})
export class AppModule { }
