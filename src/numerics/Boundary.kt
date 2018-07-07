package numerics
class Boundary {

    /**
     * Implements a constant-value (Dirichlet) boundary condition
     */
    class Dirichlet<DATA>(var vals: (Int) -> DATA) : IBoundary<DATA> {

        constructor(v: DATA) : this({ _ -> v })

        override fun attachTo(f: IArray2D<DATA>) {}

        override fun get(i: Int): DATA {
            return (vals(i))
        }

        override fun clone(): IBoundary<DATA> {
            return (Dirichlet(vals))
        }
    }


    /**
     * Implements a cylindrical (1D) or torus shaped domain
     */
    class Periodic<DATA> : IBoundary<DATA> {

        lateinit var data: IArray2D<DATA>

        override fun attachTo(f: IArray2D<DATA>) {
            data = f
        }

        override fun get(i: Int): DATA {
            return data.get(i, data.jSize() - 1)
        }

        override fun clone(): IBoundary<DATA> {
            return (Periodic())
        }
    }


    /**
     * Implements Neumann boundary condition, where the rate of change is given by
     * deltan_df_dn / deltan
     * where deltan is the grid spacing in the direction normal to the boundary
     */
    class Neumann<DATA : DoubleLike<DATA>>(var deltan_df_dn: DATA) : IBoundary<DATA> {

        lateinit var data: IArray2D<DATA>

        /**
         * @param df_dn rate of change at boundary
         * @param dn grid spacing in the direction normal to the boundary
         */
        constructor(df_dn: DATA, dn: Double) : this(df_dn.times(dn))

        override fun attachTo(f: IArray2D<DATA>) {
            data = f
        }

        override fun get(i: Int): DATA {
            return data.get(i, 0) - deltan_df_dn
        }

        override fun clone(): IBoundary<DATA> {
            return (Neumann(deltan_df_dn))
        }
    }
}
