import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

operator fun Vector3D.times(other : Vector3D) : Vector3D {
    return(Vector3D(x*other.x, y*other.y, z*other.z))
}

operator fun Vector3D.div(other : Vector3D) : Vector3D {
    return(Vector3D(x/other.x, y/other.y, z/other.z))
}

operator fun Vector3D.plus(other : Vector3D) : Vector3D {
    return(this.add(other))
}

operator fun Vector3D.minus(other : Vector3D) : Vector3D {
    return(this.subtract(other))
}

operator fun Double.times(other : Vector3D) : Vector3D {
    return other.scalarMultiply(this)
}

operator fun Vector3D.times(other : Double) : Vector3D {
    return scalarMultiply(other)
}

operator fun Vector3D.unaryMinus() : Vector3D {
    return Vector3D(-x,-y,-z)
}

fun Vector3D.volume() : Double {
    return x*y*z;
}