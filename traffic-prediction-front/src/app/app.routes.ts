import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TrafficPredictionFormComponent } from './components/traffic-prediction-form/traffic-prediction-form.component';
import { HomeComponent } from './components/home/home.component';

export const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'predict', component: TrafficPredictionFormComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
