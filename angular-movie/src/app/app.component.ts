import { Component, OnInit } from '@angular/core';
import { ThemeService } from './services/theme/theme.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
    title = 'moviepedia';
    isDarkMode!: boolean;

    constructor(private themeService: ThemeService) {}

    ngOnInit(): void {
        this.applyTheme();
        window
            .matchMedia('(prefers-color-scheme: dark)')
            .addEventListener('change', this.applyTheme.bind(this));
    }

    applyTheme() {
        this.isDarkMode = window.matchMedia(
            '(prefers-color-scheme: dark)'
        ).matches;
        const theme = this.isDarkMode
            ? 'lara-dark-indigo'
            : 'lara-light-indigo';
        this.themeService.switchTheme(theme);
    }
}
