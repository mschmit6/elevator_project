.SUFFIXES:

SOURCE = src
OUTPUT = build
CLASS_PATH = $(SOURCE)

sources = $(shell find $(SOURCE) -type f -name '*.java')
classes = $(sources:$(SOURCE)/%.java=$(OUTPUT)/%.class)
build_dirs = $(sort $(dir $(classes)))

all: $(classes)

$(build_dirs):
	mkdir -p $@

$(OUTPUT)/%.class: $(SOURCE)/%.java | $(build_dirs)
	javac -cp $(CLASS_PATH) -d $(OUTPUT) $<

test_elevator: $(classes)
	cd $(OUTPUT); java ElevatorTest $(ARGS)

test_controller: $(classes)
	cd $(OUTPUT); java ElevatorControllerTest $(ARGS)


clean:
	rm -vrf $(OUTPUT)
