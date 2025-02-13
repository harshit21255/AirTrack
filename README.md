
# AirTrack - Flight Journey Tracker

**AirTrack** is a Android application designed to visually track flight journeys. It provides details about the route, stops, visa requirements, distances, and journey progress. The app dynamically updates the UI as the user progresses through the journey and allows toggling between kilometers and miles for distance units.

---

## Features

1. **Journey Tracking**:
   - Displays the current stop, distance covered, and distance left.
   - Updates the progress bar as the user progresses through the journey.

2. **Dynamic Stops List**:
   - Loads stops from a text file (`stops.txt`).
   - Displays stops in a list with their name, distance from the previous stop, time taken from the previous stop and visa requirements.
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
  - The main activity that handles the UI and logic for tracking the journey.
  - Reads stops from `stops.txt` and updates the UI dynamically.

- **`Models.kt`**:
  - Defines the `Stop` data class, which represents a stop with its name, distance from the previous stop, and visa requirement.

- **`StopAdapter.kt`**:
  - Adapter for the RecyclerView that displays the list of stops.
  - Handles lazy loading for journeys with more than 3 stops.
  - Updates the UI based on the current stop and unit (km/mi).

### Layout Files
- **`activity_main.xml`**:
  - The main layout file that defines the UI for the app.
  - Includes a progress bar, distance indicators, journey summary, and a RecyclerView for the stops list.

- **`item_stop.xml`**:
  - The layout file for each item in the stops list.
  - Displays the stop name, distance from the previous stop, time taken from the previous stop and visa requirement.

### Resource Files
- **`res/raw/stops.txt`**:
  - A text file containing the stops for the journey.
  - Each line follows the format: `Stop Name, Distance From Previous, Visa Required (Yes or No), Time Taken From Previous`.

- **`res/values/colors.xml`**:
  - Defines the color scheme for the app.

- **`res/values/styles.xml`**:
  - Defines custom styles for buttons and the toolbar.

- **`res/values/strings.xml`**:
  - Contains the app name as a string resource.

- **`res/drawable/custom_progress_bar.xml`**:
  - A custom drawable for the progress bar with rounded corners and a custom color scheme.

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

