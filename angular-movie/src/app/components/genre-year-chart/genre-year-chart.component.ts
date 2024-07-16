import { Component } from '@angular/core';
import { Genre } from '../../interfaces/genre.model';
import { Subject } from 'rxjs';
import { GenreService } from '../../services/genre/genre.service';
import { ChartService } from '../../services/chart/chart.service';
import { MovieCountByYear } from '../../interfaces/dto';

@Component({
    selector: 'app-genre-year-chart',
    templateUrl: './genre-year-chart.component.html',
    styleUrl: './genre-year-chart.component.scss',
})
export class GenreYearChartComponent {
    genres: Genre[] = [];
    selectedGenre!: Genre;

    movieCounts!: MovieCountByYear[];

    barNumber: number = 10;
    minYear: number = 1895;

    chartData: any;
    chartOptions: any = {
        responsive: true,
        maintainAspectRatio: false,
    };

    labels: string[] = [];
    data: number[] = [];

    constructor(
        private chartService: ChartService,
        private genreService: GenreService
    ) {}

    ngOnInit(): void {
        this.genreService.getGenres().subscribe((genres) => {
            this.genres = genres;
        });
    }

    onSelectChange(): void {
        this.chartService
            .getGenreYearsData(this.selectedGenre.id)
            .subscribe((movieCounts) => {
                this.movieCounts = movieCounts;
                this.calculateChartData();
                this.chartData = {
                    labels: this.labels,
                    datasets: [
                        {
                            label:
                                'Number of ' +
                                this.selectedGenre.name.toLowerCase() +
                                ' movies',
                            data: this.data,
                        },
                    ],
                };
            });
    }

    calculateChartData(): void {
        this.data = [];
        this.labels = [];

        const interval = this.calculateInterval();
        const endYear =
            this.movieCounts[this.movieCounts.length - 1].releaseYear;

        let currentIntervalStartYear = this.minYear;
        let sum = 0;
        for (let count of this.movieCounts) {
            if (count.releaseYear >= currentIntervalStartYear + interval) {
                this.data.push(sum);
                this.addLabel(currentIntervalStartYear, interval);
                currentIntervalStartYear += interval;
                sum = 0;
            }
            sum += count.movieCount;
        }

        // Add the last interval
        this.data.push(sum);
        this.labels.push(currentIntervalStartYear + '-Today');
    }

    calculateInterval(): number {
        const maxYear =
            this.movieCounts[this.movieCounts.length - 1].releaseYear;
        return Math.floor((maxYear - this.minYear) / this.barNumber);
    }

    addLabel(year: number, interval: number): void {
        this.labels.push(year + '-' + (year + interval - 1));
    }
}
