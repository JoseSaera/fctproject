import { Component, Input, OnInit } from '@angular/core';
import { ThemeService } from '../../services/theme/theme.service';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrl: './navbar.component.scss',
})
export class NavbarComponent implements OnInit {
    @Input() isDarkMode!: boolean;
    icon!: string;

    constructor(private themeService: ThemeService) {}

    ngOnInit(): void {
        this.showIcon();
    }

    items = [
        {
            label: 'Movies',
            items: [
                { label: 'Simple search', route: '' },
                { label: 'Filter search', route: 'filter-search' },
            ],
        },
        {
            label: 'Top',
            items: [
                { label: 'Most viewed', route: 'most-viewed' },
                { label: 'Most liked', route: 'most-liked' },
            ],
        },
        {
            label: 'Charts',
            items: [
                { label: 'Genre by Rating', route: 'genre-rating-chart' },
                { label: 'Genre by Year', route: 'genre-year-chart' },
            ],
        },
    ];

    toggleDarkLightMode() {
        this.isDarkMode = !this.isDarkMode;

        const theme = this.isDarkMode
            ? 'lara-dark-indigo'
            : 'lara-light-indigo';
        this.themeService.switchTheme(theme);
        this.showIcon();
    }

    showIcon() {
        if (this.isDarkMode) {
            this.icon = 'pi pi-sun';
        } else {
            this.icon = 'pi pi-moon';
        }
    }
}
