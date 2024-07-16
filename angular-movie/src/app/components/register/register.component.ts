import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrl: './register.component.scss',
})
export class RegisterComponent {
    name: string = '';
    username: string = '';
    password: string = '';
    repeatPassword: string = '';

    validName: boolean = true;
    validUsername: boolean = true;
    validPassword: boolean = true;

    constructor(private authService: AuthService, private router: Router) {}

    register(): void {
        if (this.isValid()) {
            this.authService
                .register(this.name, this.username, this.password)
                .subscribe({
                    next: (response) => {
                        this.router.navigateByUrl('/login');
                    },
                    error: (error) => {
                        console.error('Registration error', error);
                    },
                });
        }
    }

    isValid(): boolean {
        this.validName = this.isValidName();
        this.validUsername = this.isValidUsername();
        this.validPassword = this.isValidPassword();
        return this.validName && this.validUsername && this.validPassword;
    }

    isValidName(): boolean {
        const regex = /^(?! )[A-Za-z ]{1,8}(?<! )$/;
        return regex.test(this.name);
    }

    isValidUsername(): boolean {
        const regex = /^[A-Za-z0-9]{4,10}$/;
        return regex.test(this.username);
    }

    isValidPassword(): boolean {
        const regex =
            /^(?=.+[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,10}$/;
        return regex.test(this.password);
    }
}
