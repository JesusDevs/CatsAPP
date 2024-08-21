Documentación Completa del Proyecto CATSAPP

Descripción General

CATSAPP es una aplicación Android diseñada para cargar y mostrar fotos de gatos utilizando la biblioteca Paging 3 
para la carga paginada de datos. La aplicación está construida con Jetpack Compose para la interfaz de usuario y Koin
para la inyección de dependencias. Este documento explica cómo se organizan los módulos del proyecto, cómo se inyectan 
las dependencias con Koin, y proporciona un diagrama visual de la arquitectura del proyecto.

Arquitectura del Proyecto

El proyecto se organiza en dos módulos de KOIN principales:

Módulo Network: Gestiona todas las operaciones relacionadas con la red, incluyendo la configuración del cliente HTTP y la inyección de Data Sources.
Módulo Home: Maneja la lógica de presentación, incluyendo el repositorio, los casos de uso (UseCases), el ViewModel, y la UI.

Estructura del Proyecto

Data Source: 
PexelPagingDataSource -> manejo de la paginación por medio de Paging Source y LoadResult.Page 

Data:
PexelPagingRepository -> Repository Implementation del repositorio, que utiliza el Data Source para obtener los datos.

Dominio:
PexelPagingRepositoryInterface -> Se inyecta en el Use Case.

GetCatUseCase -> Implementa la lógica de negocio y se encarga de orquestar las operaciones entre el repositorio y el ViewModel.

ViewModel: Gestiona el estado de la UI y expone los datos a la UI.

UI (Composable): Interfaz de usuario que consume los datos del ViewModel y presenta la información en la pantalla.
