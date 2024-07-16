import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../../services/auth/auth.service';
import { MenuItem } from 'primeng/api';
import { Router } from '@angular/router';

@Component({
    selector: 'app-user-item',
    templateUrl: './user-item.component.html',
    styleUrl: './user-item.component.scss',
})
export class UserItemComponent implements OnInit {
    isLoggedIn$: Observable<boolean>;
    username$: Observable<string | null>;
    items: MenuItem[] = [];

    constructor(private authService: AuthService, private router: Router) {
        this.isLoggedIn$ = new Observable<boolean>();
        this.username$ = new Observable<string | null>();
    }

    ngOnInit(): void {
        this.isLoggedIn$ = this.authService.isLoggedIn$;
        this.username$ = this.authService.username$;

        this.isLoggedIn$.subscribe((isLoggedIn) => {
            if (isLoggedIn) {
                this.items = [
                    {
                        label: 'Favorites',
                        icon: 'pi pi-heart',
                        command: () =>
                            this.router.navigateByUrl('/user-favorites'),
                    },
                    {
                        label: 'See later',
                        icon: 'pi pi-eye',
                        command: () =>
                            this.router.navigateByUrl('/user-see-later'),
                    },
                    {
                        label: 'Logout',
                        icon: 'pi pi-sign-out',
                        command: () => {
                            this.router.navigateByUrl(''), this.logout();
                        },
                    },
                ];
            } else {
                this.items = [
                    {
                        label: 'Login',
                        icon: 'pi pi-sign-in',
                        command: () => this.router.navigateByUrl('/login'),
                    },
                    {
                        label: 'Register',
                        icon: 'pi pi-user-plus',
                        command: () => this.router.navigateByUrl('/register'),
                    },
                ];
            }
        });
    }

    logout(): void {
        this.authService.logout();
    }
}
