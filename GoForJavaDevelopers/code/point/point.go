package main

import (
	"fmt"
	"math"
)

// START_COMPOSITION OMIT
// START_POINT OMIT
type Point struct {
	X, Y float64
}

// END_POINT OMIT
const (
	BLUE  = iota
	RED   = iota
	GREEN = iota
)

type ColorPoint struct {
	Point Point
	Color int
}

// END_COMPOSITION OMIT

// START_TRANSLATE OMIT
func (p Point) Translate(xDist float64, yDist float64) Point {
	return Point{p.X + xDist, p.Y + yDist}
}

// END_TRANSLATE OMIT

// START_INTERFACES OMIT
type Positioner interface {
	Coordinates() Point
}

type Distancer interface {
	DistanceTo(p Positioner) float64
}

// END_INTERFACES OMIT

// START_PS OMIT
func (p Point) Coordinates() Point {
	return p
}

func (p Point) DistanceTo(pos Positioner) float64 {
	return distanceBetween(p, pos)
}

// END_PS OMIT

// START_CPS OMIT
func (cp ColorPoint) Coordinates() Point {
	return cp.Point
}

func (cp ColorPoint) DistanceTo(pos Positioner) float64 {
	return distanceBetween(cp, pos)
}

// END_CPS OMIT

// START_DBW OMIT
func distanceBetween(a Positioner, b Positioner) float64 {
	p := a.Coordinates()
	q := b.Coordinates()
	sqOfXDist := math.Pow(p.X-q.X, 2)
	sqOfYDist := math.Pow(p.Y-q.Y, 2)
	return math.Sqrt(sqOfXDist + sqOfYDist)
}

// END_DBW OMIT

// START_ANIMAL OMIT
type Animal struct {
	Name string
	X, Y float64
}

func (a Animal) Coordinates() Point {
	return Point{X: a.X, Y: a.Y}
}

func (a Animal) DistanceTo(pos Positioner) float64 {
	thing := pos.Coordinates()
	sqOfXDist := math.Pow(a.X-thing.X, 2)
	sqOfYDist := math.Pow(a.Y-thing.Y, 2)
	return math.Sqrt(sqOfXDist + sqOfYDist)
}

// END_ANIMAL OMIT

func main() {
	// START OMIT
	p := Point{X: 1, Y: 2}
	q := ColorPoint{Point: Point{X: 1, Y: 4}, Color: BLUE}

	fmt.Printf("Point: %v\n", p)
	fmt.Printf("Color Point: %v\n", q)

	fmt.Printf("Dist b/w p and q = %v\n", p.DistanceTo(q))
	fmt.Printf("Dist b/w q and p = %v\n", q.DistanceTo(p))

	penguin := Animal{Name: "penguin", X: 1, Y: 1}
	seal := Animal{Name: "seal", X: 1, Y: 4}

	fmt.Printf("Dist b/w penguin and seal = %v\n", penguin.DistanceTo(seal))
	fmt.Printf("Dist b/w penguin and point = %v\n", penguin.DistanceTo(p))
	// END OMIT
}
