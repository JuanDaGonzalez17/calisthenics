# Rank Distribution Matrix Debugging Guide

## Changes Made

I've added comprehensive logging and error handling to debug why the distribution matrix isn't working properly.

## Modified Files

### 1. `src/services/api.ts`
- Added logging to the `getRanksByGameMode` method
- Ensured the method always returns an array (handles both array and non-array responses)
- Added console.log to see the raw response from the backend

### 2. `src/components/RankDistribution.tsx`
- Added extensive logging in `loadRankData` to see the raw data and its type
- Modified `getRankMatrix` to add defensive checks and logging for each item
- Added empty state message when no rank data is available
- Added logging to show:
  - Each item being processed
  - The rank name and division being extracted
  - The final unique ranks
  - The final matrix structure

## How to Debug

1. **Start the frontend**: The dev server should be running on port 8081
   ```
   npm run dev
   ```

2. **Open the browser console** (F12 in most browsers)

3. **Navigate to the stats page** and look at the "Distribución de Rangos por MMR" section

4. **Check the console logs** for the following information:

### Expected Console Logs:

```
getRanksByGameMode response: [...]
Raw rank data from backend: [...]
Data type: Array
Processing rankData: [...]
Processing item: {...}
Rank: [rank name], Division: [division number]
Unique ranks: [...]
Final matrix: {...}
```

## Common Issues to Look For

### Issue 1: Backend Returns Empty Array
**Symptoms**: Console shows `[]` for rank data
**Solution**: Check if the backend endpoint has data for the selected game mode

### Issue 2: Backend Returns Wrong Structure
**Symptoms**: Console shows "Invalid item structure" warnings
**Expected Structure**:
```json
[
  {
    "id": 1,
    "rango": {
      "id": 1,
      "rango": "Bronze",
      "division": 1
    },
    "gamemode": {
      "id": 1,
      "gameMode": "1v1"
    },
    "puntuacion_inicial": 0,
    "puntuacion_final": 100
  }
]
```

### Issue 3: Parameter Name Mismatch
**Symptoms**: 404 or 400 error in console
**Current endpoint**: `/rango_x_gamemode/byGamemode?gamemodeId={id}`
**Check**: Verify this matches your backend controller parameter name

### Issue 4: Authentication Issues
**Symptoms**: 401 Unauthorized error
**Solution**: Ensure you're logged in and the JWT token is valid

## Testing the Endpoint Directly

You can test the endpoint directly using curl or a tool like Postman:

```bash
curl -H "Authorization: Bearer YOUR_TOKEN" \
  "http://localhost:8080/api/rango_x_gamemode/byGamemode?gamemodeId=1"
```

## What the Matrix Should Look Like

The matrix groups ranks by name and division:

```javascript
{
  "Bronze": {
    1: { id: 1, rango: {...}, gamemode: {...}, puntuacion_inicial: 0, puntuacion_final: 100 },
    2: { id: 2, rango: {...}, gamemode: {...}, puntuacion_inicial: 100, puntuacion_final: 200 },
    3: { id: 3, rango: {...}, gamemode: {...}, puntuacion_inicial: 200, puntuacion_final: 300 },
    4: { id: 4, rango: {...}, gamemode: {...}, puntuacion_inicial: 300, puntuacion_final: 400 }
  },
  "Silver": {
    1: { ... },
    // etc.
  }
}
```

## Next Steps

1. Run the frontend and check the console logs
2. Share the console output with me so I can help identify the exact issue
3. Verify the backend endpoint is returning the expected data structure
4. Check if the parameter name `gamemodeId` matches what your backend expects

## Possible Backend Issues

If the frontend logs show the request is being made correctly but no data is returned:

1. **Check the backend controller** - Ensure the endpoint exists and the parameter name matches
2. **Check the database** - Ensure there's data for the relationship between ranks and game modes
3. **Check the backend logs** - Look for any errors or warnings
4. **Verify the response format** - Make sure it returns an array of RankGameMode objects

## Clean Up

Once the issue is resolved, you can remove the console.log statements to clean up the code.

