
CATSAPP
CATSAPP es una aplicación Android diseñada para cargar y mostrar fotos de gatos utilizando la biblioteca Paging 3 para la carga paginada de datos. La aplicación está construida con Jetpack Compose para la interfaz de usuario y Koin para la inyección de dependencias.

Construido con 🛠️

Jetpack Compose - Framework de UI moderna
Paging 3 - Librería para manejo de paginación
Koin - Librería para inyección de dependencias

Arquitectura del Proyecto
El proyecto se organiza en dos módulos principales de Koin:

Módulo Network
Gestiona todas las operaciones relacionadas con la red, incluyendo la configuración del cliente HTTP y la inyección de Data Sources.

Módulo Home
Maneja la lógica de presentación, incluyendo el repositorio, los casos de uso (UseCases), el ViewModel, y la UI.

Estructura del Proyecto
Data Source
PexelPagingDataSource: Maneja la paginación por medio de PagingSource y LoadResult.Page.

Data
PexelPagingRepository: Implementación del repositorio que utiliza el Data Source para obtener los datos.

Dominio
PexelPagingRepositoryInterface: Se inyecta en el Use Case.
GetCatUseCase: Implementa la lógica de negocio y se encarga de orquestar las operaciones entre el repositorio y el ViewModel.

ViewModel
Gestiona el estado de la UI y expone los datos a la UI.

UI (Composable)
Interfaz de usuario que consume los datos del ViewModel y presenta la información en la pantalla.
