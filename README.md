gadeApp contains the starting Servlet.

calcApp contains the grade calculation Servlet and Service.

map contains the letters to be mapped to the grade.

# Setup

### gadeApp:
	Receives parameters and calls calcApp.

### calcApp:
	take the parameters and calculates the result
	then calls map

### map:
	receives the grade parameter and displays the grade and letter for that grade on the screen.