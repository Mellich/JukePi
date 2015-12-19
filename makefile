#
# define compiler and compiler flag variables
#

.SUFFIXES: .java .class

JFLAGS = -g:none -encoding latin1
JC = javac
MD = mkdir -p
RM = rm -f -r

OUTPUT_DIR= /bin
INPUT_DIR = /src
JAR_DIR = /jar

SOURCE_FILES = source_files.mk

CLIENT_DIR = DesktopClient
SERVER_DIR = YTMusicBox
NETWORK_DIR = Network

MANIFEST = Manifest.txt

-include $(NETWORK_DIR)/$(SOURCE_FILES)
-include $(SERVER_DIR)/$(SOURCE_FILES)
-include $(CLIENT_DIR)/$(SOURCE_FILES)
#
# the default make target entry
#

all: Network Server

Network: $(NETWORK_CLASS_FILES)

Server: Network $(SERVER_CLASS_FILES)
	$(MD) $(SERVER_DIR)$(JAR_DIR)
	jar cfm $(SERVER_DIR)$(JAR_DIR)/Server.jar $(SERVER_DIR)/$(MANIFEST) -C $(NETWORK_DIR)$(OUTPUT_DIR) . -C $(SERVER_DIR)$(OUTPUT_DIR) .
	
Client: Network $(SERVER_CLASS_FILES) $(CLIENT_CLASS_FILES)
	$(MD) $(CLIENT_DIR)$(JAR_DIR)
	jar cfm $(CLIENT_DIR)$(JAR_DIR)/Client.jar $(CLIENT_DIR)/$(MANIFEST) -C $(NETWORK_DIR)$(OUTPUT_DIR) . -C $(SERVER_DIR)$(OUTPUT_DIR) . -C $(CLIENT_DIR)$(OUTPUT_DIR) .
	
#
# RM is a predefined macro in make (RM = rm -f)
#

clean:
	$(RM) $(SERVER_DIR)$(JAR_DIR)
	$(RM) $(SERVER_DIR)$(OUTPUT_DIR)
	$(RM) $(NETWORK_DIR)$(OUTPUT_DIR)
	$(RM) $(CLIENT_DIR)$(JAR_DIR)
	$(RM) $(CLIENT_DIR)$(OUTPUT_DIR)
