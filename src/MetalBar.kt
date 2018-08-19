import FiniteDifference.FiniteDiffArray
import java.lang.Math.pow
import kotlin.math.PI
import kotlin.math.sqrt

class MetalBar {
    val material = materials.BellMetal
    val N = 100             // number of points
    val dx = 0.25/N         // distance between points
    val DT = 1.0/44100.0    // timestep
    val oversampling = 3
    val dt = DT/oversampling    // finite difference timestep
    val nu = 0.00005          // damping
    val r  = 0.01           // radius of cross section of bar
    val I  = PI*pow(r,4.0)/4.0  // moment of intertia of the bar (m^4)
    val kappa = sqrt(material.E*I/material.rho)
    val mu2 = pow(kappa*dt/(dx*dx),2.0) // should be less tha 0.25 for stability
    val y = Array(3, {FiniteDiffArray(N, {0.0})})

    fun step() {
        for(j in 1..oversampling) {
            for (i in 0 until N) {
                y[2][i] = 2.0 * y[1][i] - y[0][i] - mu2 * y[1].d4_di4(i) - nu * dy_dT(i)
            }
            val stopGarbageCollection = y[0]
            y[0] = y[1]
            y[1] = y[2]
            y[2] = stopGarbageCollection
        }
    }

    fun dy_dT(i : Int) : Double {
        return y[1][i] - y[0][i]
    }

    fun hit(i : Int) {
        y[1][i] = 1.0
    }

    fun sound() : Double {
        return y[1].data.sum() - y[0].data.sum()
    }


}