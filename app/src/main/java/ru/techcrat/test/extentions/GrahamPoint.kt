package ru.techcrat.test.extentions

class GrahamPoint(
    val latitude: Double,
    val longitude:Double
):Comparable<GrahamPoint>{
    override fun compareTo(other: GrahamPoint) = this.latitude.compareTo(other.latitude)
}

fun convexHull( p: Array<GrahamPoint>):List<GrahamPoint>{
    if (p.isEmpty()) return emptyList()
    p.sort()
    val h = mutableListOf<GrahamPoint>()

    // lower hull
    for (pt in p) {
        while (h.size >= 2 && !ccw(h[h.size - 2], h.last(), pt)) {
            h.removeAt(h.lastIndex)
        }
        h.add(pt)
    }

    // upper hull
    val t = h.size + 1
    for (i in p.size - 2 downTo 0) {
        val pt = p[i]
        while (h.size >= t && !ccw(h[h.size - 2], h.last(), pt)) {
            h.removeAt(h.lastIndex)
        }
        h.add(pt)
    }

    h.removeAt(h.lastIndex)
    return h

}

fun ccw(a: GrahamPoint, b: GrahamPoint, c: GrahamPoint) =
    ((b.latitude - a.latitude) * (c.longitude - a.longitude)) > ((b.longitude - a.longitude) * (c.latitude - a.latitude))