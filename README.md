# CATSAPP 🐱

CATSAPP es una aplicación Android diseñada para cargar y mostrar fotos de gatos utilizando la biblioteca Paging 3 para la carga paginada de datos. La aplicación está construida con Jetpack Compose para la interfaz de usuario y Koin para la inyección de dependencias.

## Construido con 🛠️

![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?&logo=android&logoColor=white)
![Paging 3](https://img.shields.io/badge/Paging%203-3DDC84?&logo=android&logoColor=white)
![Koin](https://img.shields.io/badge/Koin-00AF54?&logo=kotlin&logoColor=white)

- **Jetpack Compose**: Framework de UI moderna para Android.
- **Paging 3**: Librería para manejo de paginación eficiente de grandes conjuntos de datos.
- **Koin**: Librería ligera para la inyección de dependencias en aplicaciones Kotlin.

## Arquitectura del Proyecto 🏗️

### Módulo Network 🌐

Gestiona todas las operaciones relacionadas con la red, incluyendo la configuración del cliente HTTP y la inyección de Data Sources. Este módulo es responsable de proporcionar las dependencias necesarias para interactuar con la API de Pexel.

### Módulo Home 🏡

Maneja la lógica de presentación de la aplicación. Este módulo incluye el repositorio, los casos de uso (UseCases), el ViewModel, y la UI.

## Estructura del Proyecto 📂

### Data Source

- **PexelPagingDataSource**: Maneja la paginación por medio de `PagingSource` y `LoadResult.Page`. Se encarga de la comunicación directa con la API de Pexel para obtener las fotos paginadas.

### Data

- **PexelPagingRepository**: Implementación del repositorio que utiliza el Data Source para obtener los datos de la API de Pexel. Este repositorio es el puente entre la fuente de datos y el caso de uso.

### Dominio

- **PexelPagingRepositoryInterface**: Implementa el repository en los use case.
- **GetCatUseCase**: Implementa la lógica de negocio.

### ViewModel

- **HomeViewModel**: Gestiona el estado de la UI.

### UI (Composable)

- **HomeScreen**: La interfaz de usuario que consume los datos del ViewModel y presenta la información en la pantalla.
