import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss',
})
export class LoginComponent {
    username: string = '';
    password: string = '';
    isValid: boolean = true;

    constructor(private authService: AuthService, private router: Router) {}

    login(): void {
        this.authService.authenticate(this.username, this.password).subscribe({
            next: (response) => {
                this.router.navigateByUrl('');
            },
            error: (error) => {
                this.isValid = false;
                console.error('Login error', error);
            },
        });
    }
}
