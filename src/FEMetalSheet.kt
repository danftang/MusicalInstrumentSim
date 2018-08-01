import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

// Finite element metal sheet

class FEMetalSheet {
    val material = materials.BellMetal
    val iSize : Int = 100
    val jSize : Int = 100
    val kSize : Int = 2
    val physicalSize = Vector3D(0.3,0.3,0.002) // m
    val delta : Vector3D = physicalSize/Vector3D(iSize-1.0, jSize-1.0, kSize-1.0) // equilibrium distance between elements
    val m = material.rho * delta.volume()   // mass of each element (kg)
//    val T = Vector3D(0.2, 0.2, 0.2)           // force per unit displacement on an element (Nm)
    val dt = 0.1/44100.0    // timestep (s)

    val elements : Array3D<FiniteElement3D> = Array3D(iSize, jSize, kSize, { x, y, z ->
        val vi = Vector3D(x.toDouble(), y.toDouble(), z.toDouble())
        FiniteElement3D(vi*delta, Vector3D.ZERO)
    })
    val a : Array3D<Vector3D> = Array3D(iSize-1, jSize-1, kSize-1, { _, _, _->Vector3D.ZERO})

    fun calcAcceleration() {
        a.set(Vector3D.ZERO)
        for(k in 0 until kSize-1) {
            for (j in 0 until jSize-1) {
                for (i in 0 until iSize-1) {
                    val xoffset = elements[i+1,j,k].p - elements[i,j,k].p
                    val xoffsetNorm = xoffset.norm
                    val xaccel = xoffset*(elements[i,j,k].T.x*(xoffsetNorm-delta.x)/(xoffsetNorm*m))
                    a[i,j,k] += xaccel
                    a[i+1,j,k] -= xaccel
                    // TODO: do the same for y and z
                }
            }
        }
    }


}