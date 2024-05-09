# Requirements

This code was written using Java 22.0.1. It only requires that Java and Make be installed on your system.

# Compiling

To compile the code in the source directory, simply navigate to the base directory where the Makefile is located and run "make" at the command line.

# Running Tests

Two examples/tests have been written to demo the code. One for the `Elevator` class, and another for the `ElevatorController`.

The `ElevatorTest` can be run using the command below, where the "#" symbol is replaced by a number, 1-4, to run one of the four test cases.

```
make test_elevator ARGS=#
```

The `ElevatorControllerTest` can be run using the command below, where the "#" symbol is replaced by a number, 1-4, to run one of the four test cases.

```
make test_controller ARGS=#
```
