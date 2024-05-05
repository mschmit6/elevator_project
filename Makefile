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

run: $(classes)
	cd $(OUTPUT); java ElevatorExample

clean:
	rm -vrf $(OUTPUT)
