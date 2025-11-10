# Leaderboard Implementation Guide

## Overview
Successfully implemented the leaderboard feature to display top players based on game mode and country filters using real backend data.

## Backend Endpoint
```
GET http://localhost:8080/api/jugador_x_partido_x_carro/leaderboard
```

### Query Parameters
- `idGamemode` (optional): Filter by game mode ID
- `idPais` (optional): Filter by country ID  
- `limit` (optional): Limit the number of results (default: 50)

### Response Format
```json
[
  {
    "jugadorId": 2,
    "userName": "RojasRL",
    "score": 910,
    "goles": 0,
    "asistencias": 0,
    "salvadas": 0,
    "victorias": 0,
    "partidosJugados": 0,
    "tiros": 0
  },
  {
    "jugadorId": 5,
    "userName": "AndresR",
    "score": 0,
    "goles": 0,
    "asistencias": 0,
    "salvadas": 0,
    "victorias": 0,
    "partidosJugados": 0,
    "tiros": 0
  }
]
```

## Changes Made

### 1. Added LeaderboardEntry Type (`src/types/api.ts`)
```typescript
export interface LeaderboardEntry {
  jugadorId: number;
  userName: string;
  score: number;
  goles: number;
  asistencias: number;
  salvadas: number;
  victorias: number;
  partidosJugados: number;
  tiros: number;
}
```

### 2. Added getLeaderboard Method (`src/services/api.ts`)
```typescript
async getLeaderboard(
  idGamemode?: number,
  idPais?: number,
  limit?: number
): Promise<LeaderboardEntry[]>
```

**Features:**
- Accepts optional filters for game mode, country, and result limit
- Builds query string dynamically based on provided parameters
- Returns empty array if response is not an array
- Includes error logging for debugging

### 3. Updated Leaderboard Component (`src/components/Leaderboard.tsx`)

**Key Changes:**
- Removed mock data
- Added real API integration
- Load game modes and countries on component mount
- Fetch leaderboard data when filters change
- Display loading state while fetching data
- Show empty state when no data is available
- Simplified table to show: Position, Username, Score

**State Management:**
```typescript
- countryFilter: number | undefined
- gameModes: GameMode[]
- countries: Country[]
- selectedGameMode: number | undefined
- leaderboardData: LeaderboardEntry[]
- loading: boolean
```

**Filters:**
1. **Country Filter** - Dropdown with all countries or "Todos los países"
2. **Game Mode Filter** - Dropdown with all available game modes

## Features

### 🏆 Ranking Display
- **1st Place**: Gold trophy icon
- **2nd Place**: Silver medal icon
- **3rd Place**: Bronze medal icon
- **4th and below**: Position number (e.g., #4, #5)

### 📊 Data Display
- **Position**: Visual rank indicator
- **Username**: Player's username
- **Score**: Formatted score with thousand separators

### 🔄 Real-time Filtering
- Automatically reloads when game mode changes
- Automatically reloads when country filter changes
- Shows top 50 players by default

### 💫 User Experience
- Loading state while fetching data
- Empty state when no data is available
- Responsive table layout
- Consistent styling with the rest of the app

## Usage

### Basic Usage
The leaderboard loads automatically when the page loads:
1. Fetches all game modes and countries
2. Selects the first game mode by default
3. Loads leaderboard for that game mode

### Filtering by Country
1. Click the country dropdown in the header
2. Select "Todos los países" for global leaderboard
3. Select a specific country to filter

### Changing Game Mode
1. Use the "Modo de Juego" dropdown
2. Select any available game mode
3. Leaderboard updates automatically

## API Service Methods

### getLeaderboard()
```typescript
const data = await apiService.getLeaderboard(
  1,        // idGamemode (optional)
  5,        // idPais (optional)
  50        // limit (optional)
);
```

**Examples:**

```typescript
// Global leaderboard for game mode 1, top 50
await apiService.getLeaderboard(1, undefined, 50);

// Country-specific leaderboard (Spain)
await apiService.getLeaderboard(1, 5, 50);

// All parameters optional - gets all data
await apiService.getLeaderboard();
```

## Testing

### 1. Test Game Mode Filter
- Navigate to the leaderboard
- Select different game modes
- Verify data updates

### 2. Test Country Filter
- Select "Todos los países"
- Select a specific country
- Verify filtering works correctly

### 3. Test Empty States
- If no data is available, should show appropriate message
- Loading state should display while fetching

### 4. Check Browser Console
The API service logs responses for debugging:
```
getLeaderboard response: [...]
```

## Troubleshooting

### No Data Displayed
**Possible causes:**
1. Backend is not running on port 8080
2. No data in database for selected filters
3. Authentication token is invalid

**Solutions:**
1. Check backend is running
2. Check browser console for errors
3. Verify JWT token is valid

### Wrong Data Structure
**Symptom:** Console shows errors about missing properties

**Solution:** Verify backend returns the expected structure:
```json
[
  {
    "jugadorId": number,
    "userName": string,
    "score": number
  }
]
```

### Authentication Errors
**Symptom:** 401 Unauthorized error

**Solution:** 
1. Ensure you're logged in
2. Check JWT token is being sent in headers
3. Verify token hasn't expired

## Future Enhancements

Possible improvements:
1. Add pagination for large leaderboards
2. Add search functionality to find specific players
3. Show player's country flag
4. Add player's highest rank badge
5. Add "View Profile" button for each player
6. Show player's change in position (↑ ↓)
7. Highlight current user's position
8. Add time period filter (weekly, monthly, all-time)

## Integration with Backend

Ensure your backend controller returns:
```java
@GetMapping("/leaderboard")
public ResponseEntity<List<LeaderboardEntry>> getLeaderboard(
    @RequestParam(required = false) Integer idGamemode,
    @RequestParam(required = false) Integer idPais,
    @RequestParam(required = false, defaultValue = "50") Integer limit
) {
    // Return list of LeaderboardEntry objects
}
```

## Notes

- The leaderboard shows top 50 players by default
- Scores are formatted with thousand separators for readability
- Position numbers start from 1 (not 0)
- Empty filters mean "no filter" (all data)
- Component is fully responsive and works on mobile devices

