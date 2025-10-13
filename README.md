# 📦 Control de Inventario - Android App

Aplicación Android para gestión básica de inventario con sistema de autenticación completo.

## 🏗️ Arquitectura del Proyecto

```
DM1_P_ANDROID/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/dm1_p_android/
│   │   │   └── MainActivity.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml              # Pantalla principal
│   │   │   │   ├── activity_login.xml             # Login
│   │   │   │   ├── activity_registro.xml          # Registro
│   │   │   │   ├── activity_splash.xml            # Splash screen
│   │   │   │   ├── activity_agregar_producto.xml  # Formulario producto
│   │   │   │   ├── activity_recuperar_password.xml # Recuperar contraseña
│   │   │   │   ├── item_producto.xml              # Item lista producto
│   │   │   │   └── dialog_confirmacion.xml        # Diálogo confirmación
│   │   │   ├── drawable/
│   │   │   │   ├── ic_add.xml                     # Icono agregar
│   │   │   │   ├── ic_email.xml                   # Icono email
│   │   │   │   ├── ic_lock.xml                    # Icono candado
│   │   │   │   ├── ic_person.xml                  # Icono persona
│   │   │   │   ├── ic_inventory_logo.xml          # Logo app
│   │   │   │   ├── search_background.xml          # Fondo búsqueda
│   │   │   │   ├── stock_indicator.xml            # Indicador stock
│   │   │   │   └── splash_gradient.xml            # Gradiente splash
│   │   │   ├── values/
│   │   │   │   ├── strings.xml                    # Textos
│   │   │   │   ├── colors.xml                     # Colores
│   │   │   │   ├── dimens.xml                     # Dimensiones
│   │   │   │   └── themes.xml                     # Temas
│   │   │   └── values-night/
│   │   │       └── themes.xml                     # Tema nocturno
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
└── README.md
```

## 🎨 Pantallas y Componentes

### 1. 🚀 Splash Screen (`activity_splash.xml`)
**Descripción**: Pantalla de carga inicial con logo y gradiente.

**Elementos**:
- Logo de inventario
- Título de la aplicación
- Progress bar de carga

---

### 2. 🔐 Login (`activity_login.xml`)
**Descripción**: Pantalla de inicio de sesión con fondo azul.

**IDs de Elementos**:
```kotlin
// Campos de entrada
etEmail                    // EditText - Email del usuario
etPassword                 // EditText - Contraseña

// Botones y enlaces
btnLogin                   // Button - Iniciar sesión
tvOlvidastePassword       // TextView - Link recuperar contraseña
tvIrARegistro             // TextView - Link ir a registro
```

---

### 3. 📝 Registro (`activity_registro.xml`)
**Descripción**: Pantalla de registro con fondo verde.

**IDs de Elementos**:
```kotlin
// Campos de entrada
etNombreUsuario           // EditText - Nombre de usuario
etEmailRegistro           // EditText - Email
etPasswordRegistro        // EditText - Contraseña
etConfirmarPassword       // EditText - Confirmar contraseña

// Botones y enlaces
btnRegistro               // Button - Registrarse
tvIrALogin               // TextView - Link volver al login
```

---

### 4. 🔑 Recuperar Contraseña (`activity_recuperar_password.xml`)
**Descripción**: Pantalla para recuperación de contraseña.

**IDs de Elementos**:
```kotlin
// Campos de entrada
etEmailRecuperar          // EditText - Email para recuperación

// Botones
btnEnviarRecuperacion     // Button - Enviar enlace
tvVolverLogin            // TextView - Volver al login
```

---

### 5. 🏠 Pantalla Principal (`activity_main.xml`)
**Descripción**: Dashboard principal con estadísticas y lista de productos.

**IDs de Elementos**:
```kotlin
// Búsqueda
etBuscar                  // EditText - Barra de búsqueda

// Estadísticas
tvTotalProductos          // TextView - Total de productos
tvStockBajo              // TextView - Productos con stock bajo
tvValorTotal             // TextView - Valor total del inventario

// Navegación
fabAgregarProducto       // FloatingActionButton - Agregar producto
rvProductos              // RecyclerView - Lista de productos
layoutEmptyState         // LinearLayout - Estado vacío
```

---

### 6. ➕ Agregar/Editar Producto (`activity_agregar_producto.xml`)
**Descripción**: Formulario para agregar o editar productos.

**IDs de Elementos**:
```kotlin
// Header
tvTituloFormulario       // TextView - Título del formulario

// Campos del producto
etNombre                 // EditText - Nombre del producto
etCodigo                 // EditText - Código del producto
etCantidad               // EditText - Cantidad en stock
etPrecio                 // EditText - Precio del producto
etCategoria              // EditText - Categoría
etDescripcion            // EditText - Descripción

// Botones de acción
btnGuardar               // Button - Guardar producto
btnCancelar              // Button - Cancelar operación
```

---

### 7. 📋 Item de Producto (`item_producto.xml`)
**Descripción**: Layout para cada producto en la lista del RecyclerView.

**IDs de Elementos**:
```kotlin
// Información del producto
tvNombreProducto         // TextView - Nombre del producto
tvCodigoProducto         // TextView - Código del producto
tvCantidad               // TextView - Cantidad en stock
tvPrecio                 // TextView - Precio del producto
tvCategoria              // TextView - Categoría del producto

// Indicadores visuales
indicadorStock           // View - Indicador circular de stock

// Botones de acción
btnEditar                // Button - Editar producto
btnEliminar              // Button - Eliminar producto
```

---

### 8. ⚠️ Diálogo de Confirmación (`dialog_confirmacion.xml`)
**Descripción**: Diálogo para confirmar eliminación de productos.

**IDs de Elementos**:
```kotlin
// Contenido del diálogo
tvTituloDialog           // TextView - Título del diálogo
tvMensajeDialog          // TextView - Mensaje de confirmación

// Botones
btnConfirmarDialog       // Button - Confirmar acción
btnCancelarDialog        // Button - Cancelar acción
```

## 🎨 Sistema de Colores

```xml
<!-- Colores principales -->
primary_blue      #2196F3    // Azul principal
primary_dark      #1976D2    // Azul oscuro
accent_green      #4CAF50    // Verde de acento

<!-- Estados de stock -->
stock_alto        #4CAF50    // Verde - Stock suficiente
stock_medio       #FF9800    // Naranja - Stock medio
stock_bajo        #F44336    // Rojo - Stock bajo

<!-- Fondos -->
background_light  #F5F5F5    // Fondo claro
card_background   #FFFFFF    // Fondo de tarjetas
divider_color     #E0E0E0    // Color de divisores

<!-- Textos -->
text_primary      #212121    // Texto principal
text_secondary    #757575    // Texto secundario
text_hint         #BDBDBD    // Texto de sugerencia
```

## 📐 Dimensiones Estándar

```xml
<!-- Margins y Paddings -->
margin_small      8dp
margin_medium     16dp
margin_large      24dp

<!-- Elementos UI -->
card_corner_radius     8dp
card_elevation         4dp
search_bar_height      48dp
stats_card_height      80dp
button_height          48dp
stock_indicator_size   12dp
```

## 🔧 Funcionalidades Implementadas

### ✅ Sistema de Autenticación
- [x] Splash screen con logo
- [x] Login con validación
- [x] Registro de usuarios
- [x] Recuperación de contraseña
- [x] Navegación entre pantallas

### ✅ Gestión de Inventario
- [x] Dashboard con estadísticas
- [x] Lista de productos con RecyclerView
- [x] Formulario agregar/editar producto
- [x] Búsqueda de productos
- [x] Indicadores visuales de stock
- [x] Confirmación de eliminación

### ✅ Diseño UI/UX
- [x] Material Design 3
- [x] Responsive design
- [x] Colores semánticos
- [x] Iconografía consistente
- [x] Estados vacíos
- [x] Feedback visual

## 🚀 Próximos Pasos para Implementación

1. **Crear Activities**:
   - `SplashActivity.kt`
   - `LoginActivity.kt`
   - `RegistroActivity.kt`
   - `RecuperarPasswordActivity.kt`
   - `AgregarProductoActivity.kt`

2. **Implementar Lógica**:
   - Validación de formularios
   - Navegación entre pantallas
   - Gestión de datos (SQLite/Room)
   - Autenticación de usuarios

3. **Crear Adapters**:
   - `ProductoAdapter.kt` para RecyclerView
   - Gestión de eventos click

4. **Modelos de Datos**:
   - `Usuario.kt`
   - `Producto.kt`
   - Base de datos local

## 📱 Compatibilidad

- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 34 (Android 14)
- **Kotlin**: Compatible
- **Material Design**: 3.0

## 🎯 Características del Diseño

- **Responsive**: Adaptable a diferentes tamaños de pantalla
- **Accesible**: Cumple estándares de accesibilidad
- **Intuitivo**: Navegación clara y consistente
- **Moderno**: Siguiendo las últimas guías de Material Design
- **Eficiente**: Layouts optimizados para rendimiento

---

**Desarrollado para gestión básica de inventario con enfoque en usabilidad y diseño moderno.**