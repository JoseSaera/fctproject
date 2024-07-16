import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';

import { routes } from './app.routes';
import { NavbarComponent } from './components/navbar/navbar.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MovieService } from './services/movie/movie.service';
import { MovieCardComponent } from './components/movie-card/movie-card.component';
import { SearchComponent } from './components/search/search.component';
import { ChartService } from './services/chart/chart.service';
import { GenreService } from './services/genre/genre.service';
import { ThemeService } from './services/theme/theme.service';
import { FilterBarComponent } from './components/filter-bar/filter-bar.component';
import { FilterSearchComponent } from './components/filter-search/filter-search.component';
import { GenreRatingChartComponent } from './components/genre-rating-chart/genre-rating-chart.component';
import { GenreYearChartComponent } from './components/genre-year-chart/genre-year-chart.component';
import { MostLikedComponent } from './components/most-liked/most-liked.component';
import { MostViewedComponent } from './components/most-viewed/most-viewed.component';
import { FormsModule } from '@angular/forms';
import { MenubarModule } from 'primeng/menubar';
import { ChartModule } from 'primeng/chart';
import { DropdownModule } from 'primeng/dropdown';
import { FloatLabelModule } from 'primeng/floatlabel';
import { SplitButtonModule } from 'primeng/splitbutton';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthService } from './services/auth/auth.service';
import { UserItemComponent } from './components/user-item/user-item.component';
import { UserSeeLaterComponent } from './components/user-see-later/user-see-later.component';
import { UserFavoritesComponent } from './components/user-favorites/user-favorites.component';
import { UserService } from './services/user/user.service';
import { AuthInterceptor } from './authinterceptor.interceptor';
@NgModule({
    declarations: [
        AppComponent,
        SearchComponent,
        NavbarComponent,
        MovieCardComponent,
        FilterBarComponent,
        FilterSearchComponent,
        GenreRatingChartComponent,
        GenreYearChartComponent,
        MostLikedComponent,
        MostViewedComponent,
        LoginComponent,
        RegisterComponent,
        UserItemComponent,
        UserSeeLaterComponent,
        UserFavoritesComponent,
    ],
    bootstrap: [AppComponent],
    imports: [
        CommonModule,
        RouterOutlet,
        BrowserModule,
        RouterModule.forRoot(routes),
        HttpClientModule,
        FormsModule,
        MenubarModule,
        ChartModule,
        DropdownModule,
        FloatLabelModule,
        SplitButtonModule,
    ],
    providers: [
        provideAnimationsAsync('noop'),
        MovieService,
        ChartService,
        GenreService,
        ThemeService,
        AuthService,
        UserService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
        },
    ],
})
export class AppModule {}
