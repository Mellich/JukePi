CLIENT_SOURCE_FILES = $(shell find $(CLIENT_DIR)$(INPUT_DIR) -type f \( -name \*.java ! -name \*package-info.java \))
CLIENT_CLASS_FILES = $(subst $(INPUT_DIR)/,$(OUTPUT_DIR)/, $(CLIENT_SOURCE_FILES:%.java=%.class))

CLIENT_ARTWORK = \
$(CLIENT_DIR)$(OUTPUT_DIR)/resources/skip.png \
$(CLIENT_DIR)$(OUTPUT_DIR)/resources/seekbackward.png \
$(CLIENT_DIR)$(OUTPUT_DIR)/resources/seekforward.png \
$(CLIENT_DIR)$(OUTPUT_DIR)/resources/play.png \
$(CLIENT_DIR)$(OUTPUT_DIR)/resources/pause.png

$(CLIENT_DIR)$(OUTPUT_DIR)/%.png: $(CLIENT_DIR)$(INPUT_DIR)/%.png
	@echo "Copying artwork: $<"
	@$(MD) $(CLIENT_DIR)$(OUTPUT_DIR)/resources
	@$(CP) $(CLIENT_DIR)$(INPUT_DIR)/$*.png $(CLIENT_DIR)$(OUTPUT_DIR)/$*.png



$(CLIENT_DIR)$(OUTPUT_DIR)/%.class: $(CLIENT_DIR)$(INPUT_DIR)/%.java
	@echo ""
	@echo "CLIENT"
	@echo "------"
	@echo "Creating output folder: $(CLIENT_DIR)$(OUTPUT_DIR)"
	@$(MD) $(CLIENT_DIR)$(OUTPUT_DIR)
	@echo "Compiling JukePi client"
	$(JC) $(JFLAGS) -d $(CLIENT_DIR)$(OUTPUT_DIR) -classpath $(NETWORK_DIR)$(OUTPUT_DIR)$::$(SERVER_DIR)$(OUTPUT_DIR) $(shell find $(CLIENT_DIR)$(INPUT_DIR) -name \*.java)



