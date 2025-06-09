# 3D Geometry Projection Demo

A Java-based 3D geometry visualization project demonstrating polygon projection and triangulation using two different 3D engines.

## 🎯 Features

- **Polygon Triangulation**: Uses poly2tri library for efficient polygon triangulation
- **Two 3D Environments**: 
  - JavaFX 3D implementation
  - JMonkeyEngine 3D implementation
- **Interactive Controls**:
  - Left click: Select/interact with geometries
  - Right click: Camera movement controls
  - Mouse movement: Navigate 3D scenes

## 📁 Project Structure

```
src/main/java/
├── app/                    # Main application entry points
├── fx3d/                   # JavaFX 3D implementation
├── jme3d/                  # JMonkeyEngine 3D implementation
├── model/                  # Data models and geometry definitions
└── projection/             # Polygon projection and triangulation logic
```

## 🛠️ Dependencies

- **JavaFX**: 3D scene rendering and UI (included in JDK)
- **JMonkeyEngine**: Alternative 3D engine implementation
- **poly2tri**: Fast polygon triangulation library
- **Java SE 21**: Core runtime environment

## 🚀 Getting Started

### Prerequisites
- Java 21 LTS incl. JavaFX
- Maven for dependency management

### Running the Examples

**JavaFX 3D Demo from console:**

```bash
# Run the JavaFX implementation
java -cp target/classes JavaFX3DWorldApp.Main
```

**JMonkeyEngine 3D Demo console:**

```bash
# Run the JMonkeyEngine implementation
java -cp target/classes JMonkeyWorldApp.Main
```

## 🎮 Controls

| Action | Description |
|--------|-------------|
| **Left Click** | Select/interact with 3D objects |
| **Right Click** | Enable camera movement |
| **Mouse Move** | Navigate around the 3D scene |
| **Mouse Scroll** | Zoom view (when camera enabled) |

## 🔧 Key Components

- **Projection System**: Converts 2D polygons to Delaunay Triangles with mapping 2d ->3D
- **Interactive Picking**: MouseHandlers and Ray-casting for object selection
- **Dual Rendering**: Compare JavaFX vs JMonkeyEngine performance
- **Geometry Models**: Reusable 3D shape definitions

## 📝 License

This project is for educational and demonstration purposes.