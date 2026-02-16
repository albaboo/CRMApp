# 📇 CRMApp

**CRMApp** es una aplicación Android para la gestión de contactos y seguimientos, con soporte para múltiples idiomas, modo oscuro y sincronización con Firebase Realtime Database.

More info [here](https://deepwiki.com/albaboo/CRMApp)

---

## 📌 Características

- Lista de contactos con detalles  
- Seguimientos asociados  
- Soporte de idiomas: Español, English, Català  
- Modo oscuro / claro  
- Preferencias guardadas en SharedPreferences  
- Datos almacenados en Firebase Realtime Database  
- Sonido de clic en interacciones

---

## 🚀 Capturas

![Contacts](https://github.com/user-attachments/assets/bca4740d-e4ec-4b8a-9f21-100b780a8bc4)
![Contact](https://github.com/user-attachments/assets/3565090f-ad1a-4d75-8a3d-815dcd895743)
![FollowUps](https://github.com/user-attachments/assets/ee5772eb-3368-4b89-9c35-aa3249288c7d)
![FollowUp](https://github.com/user-attachments/assets/688212ca-12cb-499a-8893-f2261c62b75a)
![Settings](https://github.com/user-attachments/assets/83a5ef5d-f8a9-4308-8766-3c8adba36eec)
![SettingsDark](https://github.com/user-attachments/assets/a07606d7-cb85-4963-9d4d-bc79e9e1bf04)
![Settings-Langs](https://github.com/user-attachments/assets/4bc9baa0-6f95-492f-9f2f-1894b2d8bb17)
![SettingsEsp](https://github.com/user-attachments/assets/bdb0506c-77f2-4282-9964-c2f265b24d7e)

---

## 🗂️ Estructura del proyecto

```
CRMApp/
├─ app/
│   ├─ src/main/java/...    # Activities, modelos, repositorios
│   ├─ res/                 # Layouts, drawables, strings
│   └─ AndroidManifest.xml
├─ build.gradle
├─ settings.gradle
├─ README.md
```

---

## 📥 Requisitos

- Android Studio Bumblebee o superior  
- Firebase Realtime Database configurado  
- Conexión a internet  
- SDK mínimo: Android 5.0 (API 21+)  

---

## ⚙️ Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/albaboo/CRMApp.git
   ```
2. Abre Android Studio y selecciona **Open an existing project**.  
3. Coloca tu archivo `google-services.json` en:
   ```
   app/google-services.json
   ```
4. Sincroniza gradle y ejecuta la app en un emulador o dispositivo.

---

## 🔧 Configuración de Firebase

1. Ve a **Firebase Console**: https://console.firebase.google.com/  
2. Crea un nuevo proyecto.  
3. Añade una app Android con el paquete: `com.albaboo.crmapp`  
4. Descarga el archivo `google-services.json` y colócalo en:
   ```
   app/google-services.json
   ```
5. Activa **Realtime Database** y crea una base de datos en modo test o según tus reglas.

---

## 🧠 Uso

- En **MainActivity** verás la lista de contactos.  
- En **FollowUps** verás los seguimientos agendados.  
- En **Settings** puedes cambiar idioma y tema.  
- Toca cualquier contacto o follow-up para ver detalles.

---

## 🧩 Librerías y Tecnologías

| Tecnología | Uso |
|------------|-----|
| Firebase Realtime Database | Almacenamiento de datos |
| Kotlin | Lenguaje principal |
| Android Jetpack | Ciclo de vida y coroutines |
| SharedPreferences | Guardar ajustes |
| SoundPool | Sonido de clic |

---


