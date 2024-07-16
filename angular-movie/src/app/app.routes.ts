import { Routes } from '@angular/router';

import { MostLikedComponent } from './components/most-liked/most-liked.component';
import { SearchComponent } from './components/search/search.component';
import { MostViewedComponent } from './components/most-viewed/most-viewed.component';
import { FilterSearchComponent } from './components/filter-search/filter-search.component';
import { GenreRatingChartComponent } from './components/genre-rating-chart/genre-rating-chart.component';
import { GenreYearChartComponent } from './components/genre-year-chart/genre-year-chart.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { UserFavoritesComponent } from './components/user-favorites/user-favorites.component';
import { UserSeeLaterComponent } from './components/user-see-later/user-see-later.component';

export const routes: Routes = [
    { path: '', component: SearchComponent },
    { path: 'most-viewed', component: MostViewedComponent },
    { path: 'most-liked', component: MostLikedComponent },
    { path: 'filter-search', component: FilterSearchComponent },
    { path: 'genre-rating-chart', component: GenreRatingChartComponent },
    { path: 'genre-year-chart', component: GenreYearChartComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'user-favorites', component: UserFavoritesComponent },
    { path: 'user-see-later', component: UserSeeLaterComponent },
];
