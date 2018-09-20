package materials

object StainlessSteel304 : PhysicalProperties {
    // Source: https://www.makeitfrom.com/material-properties/Annealed-304-Stainless-Steel
    override val E: Double = 2.0e11     // Young's modulus
    override val G: Double = 7.7e10     // Shear modulus
    override val nu: Double = 0.28      // Poisson's ratio
    override val rho: Double = 7800.0   // density
}