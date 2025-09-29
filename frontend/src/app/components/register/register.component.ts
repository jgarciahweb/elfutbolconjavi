import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  isTermsOpen = false;
  passwordStrengthText = '';
  passwordStrengthClass = '';
  passwordsMatch = true;

  constructor(private fb: FormBuilder, private toastr: ToastrService) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
      birthdate: ['', Validators.required],
      isAcceptedCookies: [false, Validators.requiredTrue]
    });
  }

  onSubmit() {
    // Primero, recalculamos fuerza y coincidencia
    this.checkPasswordStrength();
    this.checkPasswordsMatch();

    if (this.registerForm.valid && this.passwordStrengthText !== 'Débil' && this.passwordsMatch) {
      console.log("Datos enviados:", this.registerForm.value);
      // Aquí harías un POST al backend -> /api/register
    } else {
      console.log("Formulario inválido");

      // Marcamos todos los controles como touched para que se muestren los errores
      Object.values(this.registerForm.controls).forEach(control => {
        control.markAsTouched();
      });

      // Opcional: mostrar alerta global
      this.toastr.error('Por favor, corrige los errores en el formulario antes de enviar.', 'Error');
    }
  }


  onGoogleSignIn() {
    console.log("Google Sign-In (pendiente de implementar)");
    // Aquí luego integrarás Google OAuth
  }

  checkPasswordStrength() {
    const password = this.registerForm.get('password')?.value || '';
    let strength = 0;

    if (password.length >= 6) strength++;
    if (/[A-Z]/.test(password)) strength++;
    if (/[0-9]/.test(password)) strength++;
    if (/[^A-Za-z0-9]/.test(password)) strength++;

    // Ajustamos la barra y el texto según la fuerza
    if (strength <= 1) {
      this.passwordStrengthText = 'Débil';
      this.passwordStrengthClass = 'w-1/3 bg-red-500';
    } else if (strength === 2 || strength === 3) {
      this.passwordStrengthText = 'Segura';
      this.passwordStrengthClass = 'w-2/3 bg-yellow-400';
    } else if (strength >= 4) {
      this.passwordStrengthText = 'Muy segura';
      this.passwordStrengthClass = 'w-full bg-green-500';
    } else {
      this.passwordStrengthText = '';
      this.passwordStrengthClass = '';
    }
  }

  checkPasswordsMatch() {
    const password = this.registerForm.get('password')?.value || '';
    const confirm = this.registerForm.get('confirmPassword')?.value || '';
    this.passwordsMatch = password === confirm;
  }

  openTerms() {
    this.isTermsOpen = true;
  }

  closeTerms() {
    this.isTermsOpen = false;
  }
}
