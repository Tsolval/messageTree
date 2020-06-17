package net.tsolval.testbed

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PathVariable


@RestController
class HelloContainer {

   @Autowired
   ConversationService convoService

   @RequestMapping("/")
   def index() {
      'Greetings from Spring Boot!'
   }

   @RequestMapping("/test")
   def testConversation() {
      def convo = convoService.loadFromJsonFile('/testConversation.json')
      """Starting new conversation: $convo.messageType<br/>
      Sending message... <h3>$convo.tree.message</h3>

      """
   }

   @RequestMapping("/test/{node}/{reply}")
   def testConversation(@PathVariable String node, @PathVariable String reply) {
      // lookup conversation by node
      def convo = convoService.loadFromJsonFile('/testConversation.json')
      def tree = convo.tree
      def currentNode = convoService.findNodeInTree(node, tree)
      def nextNode = convoService.findNextNode(currentNode, reply)
      
      """
      Continuing conversation:  $convo.messageType<br/>
      Last message:<br/>
      
      <h3>${currentNode?.message}</h3>

      Next message: <br/>

      <h3>${nextNode?.message}</h3>
      
      """
   }

}

