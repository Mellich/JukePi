CONSOLE_SRC_FILES = \
$(CONSOLE_DIR)$(INPUT_DIR)/clientside/JukePiCmd.java

CONSOLE_CLASS_FILES = \
$(CONSOLE_DIR)$(OUTPUT_DIR)/clientside/JukePiCmd.class

$(CONSOLE_DIR)$(OUTPUT_DIR)/%.class: $(CONSOLE_DIR)$(INPUT_DIR)/%.java
	@echo ""
	@echo "CONSOLE"
	@echo "------"
	@echo "Creating output folder: $(CONSOLE_DIR)$(OUTPUT_DIR)"
	@$(MD) $(CONSOLE_DIR)$(OUTPUT_DIR)
	@echo "Compiling JukePi server"
	@$(JC) $(JFLAGS) -d $(CONSOLE_DIR)$(OUTPUT_DIR) -cp $(NETWORK_DIR)$(OUTPUT_DIR) $(CONSOLE_SRC_FILES)


