
# AirTrack - Flight Journey Tracker

**AirTrack** is a Android application designed to visually track flight journeys. It provides details about the route, stops, visa requirements, distances, and journey progress. The app dynamically updates the UI as the user progresses through the journey and allows toggling between kilometers and miles for distance units.

---

## Features

1. **Journey Tracking**:
   - Displays the current stop, distance covered, and distance left.
   - Updates the progress bar as the user progresses through the journey.

2. **Dynamic Stops List**:
   - Loads stops from a text file (`stops.txt`).
   - Displays stops in a list with their name, distance from the previous stop, and visa requirements.
   - Implements lazy loading for journeys with more than 3 stops.

3. **Visa Requirement Indicator**:
   - Shows a small rectangle box with "VISA" for stops that require a visa.
   - The box is hidden for stops that do not require a visa.

4. **Unit Conversion**:
   - Toggles between kilometers and miles for distance units.
   - Updates all distance values dynamically when the unit is changed.

5. **Progress Bar**:
   - Uses a custom-styled `ProgressBar` to visually represent the journey progress.
   - The progress bar has rounded corners and a custom color scheme.

---

## Project Structure

### Kotlin Files
- **`MainActivity.kt`**:
  - Contains the main Composable function `AirTrackApp()`
  - Implements the entire UI using Jetpack Compose
  - Handles state management for:
    - Current stop tracking
    - Distance unit conversion (km/mi)
    - Progress calculation
    - Journey statistics
  - Contains the `StopItem` Composable for individual stop displays
  - Includes the `StopsReader` object for data loading

- **`Models.kt`**:
  - Defines the `Stop` data class with properties:
    - `name`: String - Name of the stop
    - `distanceFromPrevious`: Double - Distance from the last stop
    - `visaRequired`: Boolean - Visa requirement indicator
    - `timeFromPrevious`: Int - Travel time from previous stop

### Compose UI Components
- **Main Screen Elements**:
  - App header with title
  - Progress bar showing journey completion
  - Distance statistics (covered and remaining)
  - Current stop card with highlighted border
  - Unit toggle and next stop buttons
  - Lazy scrolling list of stops

- **Stop Item Component**:
  - Individual stop cards with:
    - Stop name and visa badge
    - Distance and time information
    - Color-coded status indicators
    - Dynamic styling based on visit status

### Resource Files
- **`res/raw/stops.txt`**:
  - Contains journey data in CSV format
  - Format: `StopName,DistanceFromPrevious,VisaRequired,TimeFromPrevious`
  - Example:
    ```
    Delhi,0.0,false,0
    Dubai,2000.0,true,4
    London,5500.0,false,7
    ```

### Theme and Styling
- **Color Scheme**:
  - `DarkBackground` (0xFF0F1316)
  - `LightBackground` (0xFF122026)
  - `DarkText` (0xFF3B4E56)
  - `LightText` (0xFFD9EDDF)
  - `Green` (0xFF23E09C)
  - `Red` (0xFFDE5753)

### Data Management
- **`StopsReader` Object**:
  - Handles reading stop data from resources
  - Provides fallback default stops
  - Implements error handling for file operations
  - Parses text data into Stop objects

### UI State Management
- Uses Compose state management through:
  - `remember` for storing stop data
  - `mutableStateOf` for tracking:
    - Current stop index
    - Distance unit preference
  - `LaunchedEffect` for scroll position updates
  - `rememberLazyListState` for list scrolling

---

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/harshit21255/AirTrack.git
   ```

2. **Open the Project in Android Studio**:
   - Open Android Studio and select "Open an Existing Project."
   - Navigate to the cloned repository and select the `AirTrack` folder.

3. **Build and Run the App**:
   - Connect an Android device or start an emulator.
   - Click on the "Run" button in Android Studio to build and run the app.

---

## Usage

1. **Start the App**:
   - The app will load the stops from `stops.txt` and display the first stop.

2. **Track the Journey**:
   - The progress bar will show the journey progress.
   - The "Distance Covered" and "Distance Left" fields will update as you progress.

3. **Switch Units**:
   - Click the "Switch Units" button to toggle between kilometers and miles.

4. **Mark Next Stop**:
   - Click the "Next Stop" button to mark the next stop as reached.
   - The stops list will update to reflect the current stop.

5. **View Visa Requirements**:
   - Stops that require a visa will display a "VISA" box next to the stop name.

---

