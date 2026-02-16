# 📇 CRMApp

**CRMApp** es una aplicación Android para la gestión de contactos y seguimientos, con soporte para múltiples idiomas, modo oscuro y sincronización con Firebase Realtime Database.

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


