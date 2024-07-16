import { Component, OnInit } from '@angular/core';
import { ChartService } from '../../services/chart/chart.service';
import { Genre } from '../../interfaces/genre.model';
import { GenreService } from '../../services/genre/genre.service';

@Component({
    selector: 'app-genre-rating-chart',
    templateUrl: './genre-rating-chart.component.html',
    styleUrl: './genre-rating-chart.component.scss',
})
export class GenreRatingChartComponent implements OnInit {
    genres: Genre[] = [];
    selectedGenre!: Genre;
    average: string = '';

    chartData: any;
    chartOptions: any = {
        responsive: false,
        maintainAspectRatio: false,
    };

    chargedChartOptions: any = {
        responsive: false,
        maintainAspectRatio: false,
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Rating Interval',
                    font: {
                        size: 15,
                    },
                },
                ticks: {
                    font: {
                        size: 15,
                    },
                },
            },
        },
    };

    chartLabels: string[] = [
        '0-1',
        '1-2',
        '2-3',
        '3-4',
        '4-5',
        '5-6',
        '6-7',
        '7-8',
        '8-9',
        '9-10',
    ];

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
            .getGenreRatingData(this.selectedGenre.id)
            .subscribe((ratings) => {
                this.chartData = {
                    labels: this.chartLabels,
                    datasets: [
                        {
                            label:
                                'Number of ' +
                                this.selectedGenre.name.toLowerCase() +
                                ' movies',
                            data: ratings,
                        },
                    ],
                };
            });
        this.chartOptions = this.chargedChartOptions;
        this.chartService
            .getAverageRatingbyGenre(this.selectedGenre.id)
            .subscribe((response) => {
                this.average = 'Average rating: ' + response.toPrecision(3);
            });
    }
}
