CLIENT_SRC_FILES = \
$(CLIENT_DIR)$(INPUT_DIR)/util/SupportedSites.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/TextFieldListener.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/OptionsTableClickListener.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/PopUpMenu.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/ShowLabelThread.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/IO.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/TextTransfer.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/TablePopUpMenu.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/TableRenderer.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/layouts/DisplayGaplistsLayout.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/layouts/NewClientLayout.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/layouts/LowClientLayout.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/layouts/LoginLayout.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/layouts/ClientLayout.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/TablePopClickListener.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/PopClickListener.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/tasks/SetGaplistTask.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/tasks/SetWishlistTask.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/tasks/SetSavedGaplistsTask.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/tasks/SetContentTask.java \
$(CLIENT_DIR)$(INPUT_DIR)/util/JMultilineLabel.java \
$(CLIENT_DIR)$(INPUT_DIR)/listener/UDPListener.java \
$(CLIENT_DIR)$(INPUT_DIR)/windows/MainWindow.java \
$(CLIENT_DIR)$(INPUT_DIR)/windows/NewListWindow.java \
$(CLIENT_DIR)$(INPUT_DIR)/windows/LowClientWindow.java \
$(CLIENT_DIR)$(INPUT_DIR)/windows/Window.java \
$(CLIENT_DIR)$(INPUT_DIR)/windows/DebugWindow.java \
$(CLIENT_DIR)$(INPUT_DIR)/windows/LogIn.java \
$(CLIENT_DIR)$(INPUT_DIR)/windows/DisplayGaplistsWindow.java \
$(CLIENT_DIR)$(INPUT_DIR)/windows/SetPasswordWindow.java \
$(CLIENT_DIR)$(INPUT_DIR)/windows/OptionsWindow.java \
$(CLIENT_DIR)$(INPUT_DIR)/windows/PasswordWindow.java \
$(CLIENT_DIR)$(INPUT_DIR)/connection/Collector.java \
$(CLIENT_DIR)$(INPUT_DIR)/windows/AcknowledgeWindow.java \
$(CLIENT_DIR)$(INPUT_DIR)/start/ClientStarter.java 

CLIENT_CLASS_FILES = \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/SupportedSites.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/TextFieldListener.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/OptionsTableClickListener.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/PopUpMenu.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/ShowLabelThread.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/IO.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/TextTransfer.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/TablePopUpMenu.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/TableRenderer.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/layouts/DisplayGaplistsLayout.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/layouts/NewClientLayout.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/layouts/LowClientLayout.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/layouts/LoginLayout.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/layouts/ClientLayout.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/TablePopClickListener.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/PopClickListener.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/tasks/SetGaplistTask.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/tasks/SetWishlistTask.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/JMultilineLabel.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/tasks/SetSavedGaplistsTask.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/util/tasks/SetContentTask.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/listener/UDPListener.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/windows/MainWindow.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/windows/NewListWindow.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/windows/LowClientWindow.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/windows/Window.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/windows/DebugWindow.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/windows/LogIn.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/windows/DisplayGaplistsWindow.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/windows/SetPasswordWindow.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/windows/OptionsWindow.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/windows/PasswordWindow.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/connection/Collector.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/windows/AcknowledgeWindow.class \
$(CLIENT_DIR)$(OUTPUT_DIR)/start/ClientStarter.class 

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
	@$(JC) $(JFLAGS) -d $(CLIENT_DIR)$(OUTPUT_DIR) -classpath $(NETWORK_DIR)$(OUTPUT_DIR)$::$(SERVER_DIR)$(OUTPUT_DIR) $(CLIENT_SRC_FILES)



