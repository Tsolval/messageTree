package net.tsolval.testbed

import groovy.json.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class ConversationService {

   def loadFromJsonFile(String filename) {
      def jsonString = getClass().getResource(filename).text
      ObjectMapper om = new ObjectMapper()
      om.readValue(jsonString, Conversation.class)
   }

   def findNodeInTree(String id, ConversationNode tree) {
      if (id == tree?.nodeId) {
         return tree
      }

      def foundNode = null
      def responseNodes = tree.responses.collect { entry -> entry.value }

      if (!responseNodes) {
         return foundNode 
      }

      for (ConversationNode node : responseNodes) {
         foundNode = findNodeInTree(id, node)
         if (foundNode) { return foundNode }
      }
   }

   def findNextNode(ConversationNode node, String reply) {
      //def pattern = ~"\\b(six|sixty)\\b"
      //def matcher = "The year was nineteen sixty five." =~ pattern
      //println matcher[0..-1]
   
      for (Map.Entry entry : node.responses) {

         if (reply =~ ~/${entry.key}/) {
            return entry.value
         }
      }
      return null
   }
}
