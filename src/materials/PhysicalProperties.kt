package materials

interface PhysicalProperties {
    val E : Double      // Youngs modulus - stress/strain (N/m^2)
    val G : Double      // Shear modulus - shear stress/shear strain (N/m^2)
    val nu : Double     // Poisson's ratio - transverse strain/axial strain
    val rho : Double    // Density (Kg/m^3)
}