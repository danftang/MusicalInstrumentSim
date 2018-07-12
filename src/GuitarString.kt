class GuitarString {
    val N = 50             // number of masses
    val dx = 1.0 / 200.0      // distance between masses (m)
    val rho = 0.1           // weight of string per metre (kg/m)
    val m = rho * dx          // weight of each mass (kg)
    val T = 50              // tension in the string (N)
    val dt = 0.00000002           // timestep (s)
    val f = 0.0005           // energy loss due to air friction (Ns/m)
    val fBridge= 0.1       // energy loss to the bridge (Ns/dx)
    val fNeck = 0.05         // energy loss to the neck (Ns/dx)

    val x = DoubleArray(N, { 0.0 }) // string position, initialised at equilibrium
    val v = DoubleArray(N, { 0.0 }) // velocity of string, initially at rest

    fun step(DT :  Double) {
        var t = 0.0
        while(t<DT) {
            step()
            t += dt
        }
    }

    fun step() {
        val a = DoubleArray(N, {i ->
            val friction = if(i<2) fBridge else if(i > N-3) fNeck else f
            T*d2_dx2(x,i)/(m*dx) - friction*v[i]/m
        }) // acceleration of the string
        for(i in 0 until N) {
            x[i] += v[i]*dt + 0.5*a[i]*dt*dt
            v[i] += a[i]*dt
        }
    }

    fun hit(i : Int) {
        v[i] = 1.0
    }

    fun pluck(i : Int) {
        val a = 0.001
        for(j in 0 until x.size) {
            if(j <= i) {
                x[j] = (j+1)*a/(i+1)
            } else {
                x[j] = (x.size-j)*a/(x.size-i)
            }
        }
    }

    fun d2_dx2(a : DoubleArray, i : Int) : Double {
        if(i == 0) return (a[i+1] - 2.0*a[i])
        if(i == a.size-1) return (a[i-1] - 2.0*a[i])
        return (a[i+1] - 2.0*a[i] + a[i-1])
    }

    fun kineticEnergy() : Double {
        return v.fold(0.0, {E, v -> E + 0.5*m*v*v})
    }

    fun forceOnBridge() : Double {
        return T*x[0]/dx
    }

}