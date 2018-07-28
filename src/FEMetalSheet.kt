import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

// Finite element metal sheet

class FEMetalSheet {
    val xSize : Int = 100
    val ySize : Int = 100
    val zSize : Int = 2
    val physicalSize = Vector3D(0.3,0.3,0.002) // m
    val delta : Vector3D = physicalSize/Vector3D(xSize-1.0, ySize-1.0, zSize-1.0) // equilibrium distance between elements
    val rho = 8000.0          // density of material (kg/m^3)
    val m = rho * delta.volume()   // mass of each element (kg)
    val T = Vector3D(0.2, 0.2, 0.2)           // force per unit displacement on an element (Nm)
    val dt = 0.1/44100.0    // timestep (s)

    val elements : Array3D<FiniteElement3D> = Array3D(xSize, ySize, zSize, {x,y,z ->
        val vi = Vector3D(x.toDouble(), y.toDouble(), z.toDouble())
        FiniteElement3D(vi*delta, Vector3D.ZERO, getT(x,y,z))
    })
    val a : Array3D<Vector3D> = Array3D(xSize-1, ySize-1, zSize-1, {_,_,_->Vector3D.ZERO})

    fun calcAcceleration() {
        a.set(Vector3D.ZERO)
        for(k in 0 until zSize-1) {
            for (j in 0 until ySize-1) {
                for (i in 0 until xSize-1) {
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

    fun getT(x : Int, y : Int, z : Int) : Vector3D {
        return Vector3D(
                if(x<xSize-2) T.x else 0.0,
                if(y<ySize-2) T.y else 0.0,
                if(z<zSize-2) T.z else 0.0
                )
    }

}