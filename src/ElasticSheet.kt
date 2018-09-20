class ElasticSheet {
    val X = 90             // number of masses horizontallu
    val Y = 90             // numner of masses vertically
    val dx = 0.5 / (X+1)    // distance between masses (m)
    val dy = 0.5 / (Y+1)    // distance between masses (m)
    val rho = 0.02          // mass of sheet per square metre (kg/m^2)
    val m = rho * dx * dy   // mass of each point (kg)
    val T = 0.2           // force per unit displacement (N)
    val dt = 0.1/44100.0    // timestep (s)
    val fAir = 0.00001       // energy loss due to air friction (Ns/m)
    val fBoundary = 0.0001       // energy loss at the boundaries (Ns/dx)
    val isClampedN = true   // is the north boundary clamped (or free)
    val isClampedS = true   // is the south boundary clamped (or free)
    val isClampedE = true   // is the east boundary clamped (or free)
    val isClampedW = true   // is the west boundary clamped (or free)

    val y = DoubleArray2D(X,Y, {_,_ -> 0.0 }) // string position, initialised at equilibrium
    val v = DoubleArray2D(X,Y, {_,_ -> 0.0 }) // velocity of string, initially at rest
    val a = DoubleArray2D(X,Y) // acceleration of string (diagnostic)
    val f = DoubleArray2D(X,Y, this::friction)

    fun step(DT :  Double) {
        var t = 0.0
        while(t<DT) {
            step()
            t += dt
        }
    }


    fun step() {
        for(i in 0 until X) {
            for(j in 0 until Y) {
                a[i, j] = acceleration(i, j)
            }
        }
        for(i in 0 until X) {
            for(j in 0 until Y) {
                val yi = y[i,j]
                y[i,j] += v[i,j] * dt + a[i,j] * dt * dt
                v[i,j] = (y[i,j] - yi) / dt
            }
        }
    }

    fun hit(i : Int, j : Int) {
        v[i,j] = 1.0
    }


    fun acceleration(i : Int, j : Int) : Double {
        return ((d2_di2(y,i,j)/dx + d2_dj2(y,i,j)/dy)*T - f[i,j]*v[i,j])/m
    }

    fun friction(i: Int, j: Int) : Double {
        return if((isClampedW && i<1) || (isClampedS && j<1) || (isClampedE && i>X-2) || (isClampedN && j>Y-2))
            fBoundary
        else
            fAir
    }


    fun d2_di2(a: DoubleArray2D , i : Int, j : Int) : Double {
        if(i == 0) {
            if(isClampedW)
                return a[i+1,j] - 2.0*a[i,j]
            else
                return a[i+1,j] - a[i,j]
        }
        if(i == a.xSize-1) {
            if(isClampedE)
                return a[i-1,j] - 2.0*a[i,j]
            else
                return a[i-1,j] - a[i,j]
        }
        return a[i+1,j] - 2.0*a[i,j] + a[i-1,j]
    }

    fun d2_dj2(a: DoubleArray2D , i : Int, j : Int) : Double {
        if(j == 0) {
            if(isClampedS)
                return a[i,j+1] - 2.0 * a[i,j]
            else
                return a[i,j+1] - a[i,j]
        }
        if(j == a.ySize-1) {
            if(isClampedN)
                return a[i,j-1] - 2.0*a[i,j]
            else
                return a[i,j-1] - a[i,j]
        }
        return a[i,j+1] - 2.0*a[i,j] + a[i,j-1]
    }

    fun sound() : Double {
        return v.data.sum()
    }

}