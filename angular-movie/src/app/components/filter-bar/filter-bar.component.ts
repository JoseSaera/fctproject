import {
    Component,
    EventEmitter,
    Input,
    OnChanges,
    Output,
    SimpleChanges,
} from '@angular/core';

import { Genre } from '../../interfaces/genre.model';
import { YearRangeDTO } from '../../interfaces/dto';

@Component({
    selector: 'app-filter-bar',
    templateUrl: './filter-bar.component.html',
    styleUrl: './filter-bar.component.scss',
})
export class FilterBarComponent implements OnChanges {
    @Input() genres!: Genre[];
    @Input() selectedGenres: number[] = [];

    @Input() yearRange: YearRangeDTO = { minYear: 0, maxYear: 0 };
    @Input() selectedMinYear: number = 0;
    @Input() selectedMaxYear: number = 0;

    @Output() filterChange = new EventEmitter<any>();

    btnAllName: string = 'Deselect all';

    constructor() {}

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['genres'] && changes['genres'].currentValue) {
            this.genres.forEach((genre) => {
                genre.isSelected = true;
                this.selectedGenres.push(genre.id);
            });
        }

        if (changes['yearRange'] && changes['yearRange'].currentValue) {
            this.selectedMinYear = this.yearRange.minYear;
            this.selectedMaxYear = this.yearRange.maxYear;
        }
    }

    toogleGenreSelection(genreId: number): void {
        const index = this.selectedGenres.indexOf(genreId);
        if (index > -1) {
            this.selectedGenres.splice(index, 1);
        } else {
            this.selectedGenres.push(genreId);
        }
        this.onGenresChange();
    }

    isSelected(genreId: number): boolean {
        return this.selectedGenres.includes(genreId);
    }

    toogleBtnAll(): void {
        if (this.genres.length == this.selectedGenres.length) {
            this.selectedGenres = [];
        } else {
            for (let genre of this.genres) {
                if (!this.selectedGenres.includes(genre.id)) {
                    this.selectedGenres.push(genre.id);
                }
            }
        }
        this.onGenresChange();
    }

    onGenresChange(): void {
        if (this.genres.length == this.selectedGenres.length) {
            this.btnAllName = 'Deselect All';
        } else {
            this.btnAllName = 'Select All';
        }
        this.emitFilterChange();
    }

    onYearsChange(event: any, isMin: boolean, isBlur: boolean): void {
        const input = event.target as HTMLInputElement;
        if (input.value.length > 4) {
            input.value = input.value.slice(0, 4);
        }
        if (isBlur && !this.isValidYear()) {
            this.resetYear(isMin);
        }
        if (this.isValidYear()) {
            this.emitFilterChange();
        }
    }

    isValidYear(): boolean {
        return (
            this.yearRange.minYear <= this.selectedMinYear &&
            this.selectedMinYear <= this.selectedMaxYear &&
            this.selectedMaxYear <= this.yearRange.maxYear
        );
    }

    resetYear(isMin: boolean): void {
        if (isMin) {
            this.selectedMinYear = this.yearRange.minYear;
        } else {
            this.selectedMaxYear = this.yearRange.maxYear;
        }
    }

    emitFilterChange(): void {
        this.filterChange.emit({
            selectedGenres: this.selectedGenres,
            selectedMinYear: this.selectedMinYear,
            selectedMaxYear: this.selectedMaxYear,
        });
    }
}
