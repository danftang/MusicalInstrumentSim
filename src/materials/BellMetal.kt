package materials

object BellMetal : PhysicalProperties {
    // Source : https://www.makeitfrom.com/material-properties/UNS-C91300-Alloy-A-Bell-Metal
    override val E: Double = 1.0e11     // Young's modulus
    override val G: Double = 3.8e10     // Shear modulus
    override val nu: Double = 0.34      // Poisson's ratio
    override val rho: Double = 8600.0   // density
}
