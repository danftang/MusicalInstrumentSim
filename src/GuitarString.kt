
class GuitarString {
    val N = 120             // number of masses
    val dx = 0.63 / (N+1)      // distance between masses (m)
    val rho = 0.02           // weight of string per metre (kg/m)
    val m = rho * dx          // weight of each mass (kg)
    val T = 300.0              // tension in the string (N)
    val dt = 1.0/44100.0//0.000001           // timestep (s)
    val f0 = 0.0005           // energy loss due to air friction (Ns/m)
    val fBridge= 0.01       // energy loss to the bridge (Ns/dx)
    val fNeck = 0.005         // energy loss to the neck (Ns/dx)

    val y = DoubleArray(N, { 0.0 }) // string position, initialised at equilibrium
    val v = DoubleArray(N, { 0.0 }) // velocity of string, initially at rest
    val a = DoubleArray(N) // acceleration of string (diagnostic)
    val f = DoubleArray(N, this::friction)

    fun step(DT :  Double) {
        var t = 0.0
        while(t<DT) {
            step()
            t += dt
        }
    }


    fun step() {
        step3rdOrder()
    }

    fun step2ndOrder() {
        for(i in 0 until N) {
            a[i] = acceleration(i)
        }
        for(i in 0 until N) {
            y[i] += v[i]*dt + 0.5*a[i]*dt*dt
            v[i] += a[i]*dt
        }
    }

    fun step2ndOrderv2() {
        for(i in 0 until N) {
            a[i] = acceleration(i)
        }
        for(i in 0 until N) {
            val yi = y[i]
            y[i] += v[i]*dt + a[i]*dt*dt
            v[i] = (y[i]-yi)/dt
        }
    }


    fun step3rdOrder() {
        step2ndOrder()
        // calculate coefficients for tridiagonal algorithm
        val D = DoubleArray(N, {i ->
            a[i] - acceleration(i)
        })
        val A = (dt*dt*dt/6.0)*T/(m*dx)
        val B = DoubleArray(N, { i ->
            -2.0 * A - (dt*dt/2.0)*f[i]/m - dt
        })
        val C = DoubleArray(N, {A})
        // forward sweep of tridiagonal
        C[0] = C[0]/B[0]
        D[0] = D[0]/B[0]
        for(i in 1 until N) {
            val z = B[i] - A*C[i-1]
            C[i] = C[i]/z
            D[i] = (D[i] - A*D[i-1])/z
        }
        // reverse sweep of tridiagonal
        for(i in N-2 downTo 0) {
            D[i] -= C[i]*D[i+1]
        }
        // apply third order perturbation
        for(i in 0 until N) {
            y[i] += D[i]*dt*dt*dt/6.0
            v[i] += D[i]*dt*dt/2.0
        }
    }

    inline fun acceleration(i : Int) : Double {
        return T*d2_di2(y,i)/(m*dx) - f[i]*v[i]/m
    }

    inline fun friction(i: Int) : Double {
        return if(i<3) fBridge else if(i > N-4) fNeck else f0
    }

    fun hit(i : Int) {
        v[i] = 1.0
    }

    fun pluck(i : Int) {
        val a = 0.005
        for(j in 0 until y.size) {
            if(j <= i) {
                y[j] = (j+1)*a/(i+1)
            } else {
                y[j] = (y.size-j)*a/(y.size-i)
            }
        }
    }

    fun d2_di2(a : DoubleArray, i : Int) : Double {
        if(i == 0) return (a[i+1] - 2.0*a[i])
        if(i == a.size-1) return (a[i-1] - 2.0*a[i])
        return (a[i+1] - 2.0*a[i] + a[i-1])
    }

    fun kineticEnergy() : Double {
        return v.fold(0.0, {E, v -> E + 0.5*m*v*v})
    }

    fun forceOnBridge() : Double {
        return T*y[0]/dx
    }

}