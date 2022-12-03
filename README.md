# compose-graphs
Jetpack compose graphing library (multiplatform)

## How to use? (mobile & desktop)

```
PieGraph(
    listOf(
        GraphData(float, "string", Color(0xFF6495ED)),
        GraphData(float, "name", Color(0xFF33C4FF)),
    )
)
```
## Example
```
PieGraph(
    listOf(
        GraphData(39.0f, "apples", Color(0xFF6495ED)),
        GraphData(104.0f, "oranges", Color(0xFF33C4FF)),
        GraphData(25f, "bananas", Color(0xFF337AFF))
    )
)
```
