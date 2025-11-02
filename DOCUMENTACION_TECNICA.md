# 📱 Control de Inventario - Documentación Técnica

## 📋 Índice
1. [Arquitectura del Proyecto](#arquitectura)
2. [Componentes Principales](#componentes)
3. [Flujo de Datos](#flujo-de-datos)
4. [Guía de Implementación](#guía-de-implementación)
5. [API y Servicios](#api-y-servicios)
6. [Base de Datos](#base-de-datos)

---

## 🏗️ Arquitectura

### Estructura de Paquetes
```
com.example.dm1_p_android/
├── adapter/          # Adapters para RecyclerView
├── data/            # Acceso a datos locales (SQLite)
├── entity/          # Modelos de datos
├── network/         # Servicios de red (API, Firebase)
├── ui/              # Fragments de la UI
├── utils/           # Utilidades (SessionManager)
└── Activities       # Activities principales
```

---

## 🔧 Componentes Principales

### 1. **Sistema de Autenticación**

#### SessionManager
Gestiona la sesión del usuario usando SharedPreferences.

**Métodos principales:**
- `login(email, name)`: Inicia sesión
- `logout()`: Cierra sesión
- `isLoggedIn()`: Verifica si hay sesión activa

**Credenciales de prueba:**
```
Email: admin@test.com
Password: 123456

Email: user@test.com
Password: 123456
```

#### Flujo de Navegación
```
SplashActivity
    ↓
¿Está logueado?
    ├─ Sí → MainActivity
    └─ No → LoginActivity → MainActivity
```

---

### 2. **Consumo de API**

#### ApiService
Consume la API de FakeStore para obtener productos.

**Endpoint:** `https://fakestoreapi.com/products`

**Métodos:**
- `getProducts()`: Obtiene lista de productos
- `parseProducts(json)`: Parsea JSON manualmente

**Estructura de datos:**
```json
{
  "id": 1,
  "title": "Product Name",
  "price": 109.95,
  "description": "Description",
  "category": "electronics",
  "image": "https://...",
  "rating": {
    "rate": 3.9,
    "count": 120
  }
}
```

---

### 3. **Firebase Integration**

#### FirebaseService
Sincroniza productos con Firebase Realtime Database.

**Métodos:**
- `subirProducto(producto)`: Sube un producto
- `subirProductos(productos)`: Sube múltiples productos (batch)
- `obtenerProductos()`: Obtiene productos en tiempo real (Flow)
- `eliminarProducto(id)`: Elimina un producto

**Estructura en Firebase:**
```
inventario-2f11e/
└── productos/
    ├── 1/
    │   ├── id: 1
    │   ├── title: "..."
    │   ├── price: 109.95
    │   └── rating: {...}
    ├── 2/
    └── ...
```

---

### 4. **RecyclerView y Adapter**

#### ProductoAdapter
Muestra productos en lista con funcionalidades de ver y eliminar.

**Características:**
- Indicador de color según rating (verde/naranja/rojo)
- Botón "Ver" para abrir detalles
- Botón "Eliminar" con confirmación
- Actualización dinámica de datos

**Lógica de colores:**
```kotlin
rating < 3.0  → Rojo (stock_bajo)
rating < 4.0  → Naranja (stock_medio)
rating >= 4.0 → Verde (stock_alto)
```

---

## 🔄 Flujo de Datos

### Carga de Productos
```
1. InicioFragment.cargarProductosAPI()
2. ApiService.getProducts()
3. Parsea JSON → List<ApiProduct>
4. FirebaseService.subirProductos()
5. Actualiza UI con productos
```

### Eliminación de Productos
```
1. Usuario hace click en "Eliminar"
2. Muestra diálogo de confirmación
3. FirebaseService.eliminarProducto()
4. Elimina de lista local
5. Actualiza RecyclerView
```

### Filtrado y Ordenamiento
```
1. Usuario selecciona filtro/orden
2. Aplica filtro por categoría
3. Aplica búsqueda por texto
4. Ordena según criterio
5. Actualiza RecyclerView
```

---

## 📱 Guía de Implementación

### Agregar un Nuevo Producto

```kotlin
// 1. Crear el producto
val producto = ApiProduct(
    id = 999,
    title = "Nuevo Producto",
    price = 99.99,
    description = "Descripción",
    category = "electronics",
    image = "url",
    rating = Rating(4.5, 100)
)

// 2. Subir a Firebase
lifecycleScope.launch {
    val resultado = firebaseService.subirProducto(producto)
    if (resultado) {
        Toast.makeText(context, "Producto agregado", Toast.LENGTH_SHORT).show()
    }
}
```

### Escuchar Cambios en Firebase

```kotlin
lifecycleScope.launch {
    firebaseService.obtenerProductos().collect { productos ->
        // Actualizar UI con productos
        productoAdapter.updateData(productos)
    }
}
```

### Implementar Búsqueda

```kotlin
etBuscar.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        val query = s.toString()
        val filtrados = productos.filter {
            it.title.contains(query, ignoreCase = true)
        }
        productoAdapter.updateData(filtrados)
    }
})
```

---

## 🔐 API y Servicios

### Configuración de Firebase

**1. Agregar google-services.json**
```
app/google-services.json
```

**2. Dependencias en build.gradle.kts**
```kotlin
implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
implementation("com.google.firebase:firebase-database-ktx")
```

**3. Permisos en AndroidManifest.xml**
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### Configuración de API

**URL Base:** `https://fakestoreapi.com`

**Endpoints disponibles:**
- GET `/products` - Todos los productos
- GET `/products/{id}` - Producto específico
- GET `/products/categories` - Categorías

---

## 💾 Base de Datos

### SharedPreferences (Sesión)
```
Archivo: user_session
Claves:
- is_logged_in: Boolean
- user_email: String
- user_name: String
```

### Firebase Realtime Database
```
Nodo: productos/
Estructura: {id}/
  - id: Int
  - title: String
  - price: Double
  - description: String
  - category: String
  - image: String
  - rating: {
      rate: Double
      count: Int
    }
```

---

## 🎨 Recursos de Diseño

### Colores Principales
```xml
primary_blue:    #2196F3
primary_dark:    #1976D2
accent_green:    #4CAF50
stock_bajo:      #F44336
stock_medio:     #FF9800
stock_alto:      #4CAF50
```

### Dimensiones Estándar
```xml
margin_small:    8dp
margin_medium:   16dp
margin_large:    24dp
card_radius:     8dp
card_elevation:  4dp
```

---

## 🚀 Próximas Mejoras

- [ ] Implementar caché offline
- [ ] Agregar paginación en lista
- [ ] Implementar búsqueda por voz
- [ ] Agregar gráficos de estadísticas
- [ ] Implementar notificaciones push
- [ ] Agregar modo oscuro completo

---

## 📞 Soporte

Para dudas o problemas:
- Email: soporte@inventario.com
- GitHub: [Repositorio del proyecto]

---

**Versión:** 1.0  
**Última actualización:** 2024  
**Desarrollado con:** Kotlin, Firebase, Material Design 3