package com.youtubagent.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF070D1D)
private val CardBg = Color(0xFF101A31)
private val Pink = Color(0xFFFF3478)
private val Muted = Color(0xFFA8B8D8)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme(colorScheme = darkColorScheme(primary=Pink, background=Bg, surface=CardBg)) { AgentApp() } }
    }
}

private data class Tool(val icon: androidx.compose.ui.graphics.vector.ImageVector, val title: String, val detail: String, val prompt: String)
private val tools = listOf(
    Tool(Icons.Default.Description,"AI Script Writer","Hooks, intros and full video scripts","Write a YouTube video script"),
    Tool(Icons.Default.Image,"Title & Thumbnail","Clickable title options and thumbnail concepts","Create YouTube titles and thumbnail concepts"),
    Tool(Icons.Default.Search,"YouTube SEO","Keywords, description and tags","Optimize my YouTube SEO"),
    Tool(Icons.Default.Bolt,"Shorts Ideas","Short-form video ideas and hooks","Give me viral-style YouTube Shorts ideas"),
    Tool(Icons.Default.CalendarMonth,"Content Planner","Weekly upload plan","Create a weekly YouTube content calendar"),
    Tool(Icons.Default.ChatBubbleOutline,"Comment Assistant","Helpful comment replies","Draft replies to these YouTube comments"),
    Tool(Icons.Default.BarChart,"Retention Analyzer","Review audience-retention information","Analyze my audience retention data"),
    Tool(Icons.Default.VerifiedUser,"Channel Audit","Channel improvement checklist","Audit my YouTube channel")
)

@Composable
private fun AgentApp() {
    var selected by remember { mutableStateOf<Tool?>(null) }
    var prompt by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var tab by remember { mutableStateOf("Home") }
    Surface(Modifier.fillMaxSize(), color=Bg) {
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth().padding(horizontal=20.dp, vertical=18.dp), verticalAlignment=Alignment.CenterVertically) {
                Box(Modifier.size(48.dp).background(Pink, RoundedCornerShape(15.dp)), contentAlignment=Alignment.Center) {
                    Icon(Icons.Default.PlayArrow, null, tint=Color.White, modifier=Modifier.size(34.dp))
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("YouTube Agent", fontSize=23.sp, fontWeight=FontWeight.ExtraBold, color=Color.White)
                    Text("Your creator workspace", color=Muted, fontSize=13.sp)
                }
                Icon(Icons.Default.Settings, "Settings", tint=Color.White)
            }
            Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal=18.dp)) {
                if (selected == null) {
                    Spacer(Modifier.height(8.dp))
                    Text("Hello, Creator 👋", color=Color.White, fontSize=28.sp, fontWeight=FontWeight.Bold)
                    Text("What are we making today?", color=Muted, fontSize=16.sp)
                    Spacer(Modifier.height(20.dp))
                    Column(Modifier.fillMaxWidth().background(androidx.compose.ui.graphics.Brush.linearGradient(listOf(Color(0xFF432080),Color(0xFF97144D))), RoundedCornerShape(22.dp)).padding(20.dp)) {
                        Text("✦ AI CREATOR ASSISTANT", color=Color.White, fontSize=13.sp, fontWeight=FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text("Plan, write, optimize and grow your channel.", color=Color.White, fontSize=22.sp, fontWeight=FontWeight.Bold)
                        Spacer(Modifier.height(14.dp))
                        Button(onClick={ selected=tools[0]; prompt=tools[0].prompt }, colors=ButtonDefaults.buttonColors(containerColor=Pink)) { Text("Start creating  →") }
                    }
                    Spacer(Modifier.height(24.dp))
                    Row(verticalAlignment=Alignment.CenterVertically) {
                        Text("Creator Tools", color=Color.White, fontSize=22.sp, fontWeight=FontWeight.Bold, modifier=Modifier.weight(1f))
                        Text("8 tools", color=Muted)
                    }
                    Spacer(Modifier.height(12.dp))
                    tools.chunked(2).forEach { pair ->
                        Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.spacedBy(10.dp)) {
                            pair.forEach { tool ->
                                Column(Modifier.weight(1f).padding(bottom=10.dp).background(CardBg, RoundedCornerShape(17.dp)).clickable { selected=tool; prompt=tool.prompt; result="" }.padding(13.dp).heightIn(min=112.dp)) {
                                    Icon(tool.icon, null, tint=Pink, modifier=Modifier.size(26.dp))
                                    Spacer(Modifier.height(9.dp))
                                    Text(tool.title, color=Color.White, fontWeight=FontWeight.Bold, fontSize=14.sp)
                                    Spacer(Modifier.height(4.dp))
                                    Text(tool.detail, color=Muted, fontSize=11.sp, lineHeight=15.sp)
                                }
                            }
                            if (pair.size == 1) Spacer(Modifier.weight(1f))
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("More tools can be added as the backend is connected.", color=Muted, fontSize=12.sp)
                } else {
                    Text("‹  All tools", color=Pink, modifier=Modifier.clickable { selected=null; result="" }.padding(vertical=8.dp))
                    Text(selected!!.title, color=Color.White, fontSize=26.sp, fontWeight=FontWeight.Bold)
                    Text(selected!!.detail, color=Muted, modifier=Modifier.padding(top=5.dp, bottom=18.dp))
                    OutlinedTextField(value=prompt, onValueChange={prompt=it}, modifier=Modifier.fillMaxWidth(), label={Text("What do you need?")}, minLines=3, colors=OutlinedTextFieldDefaults.colors(focusedTextColor=Color.White, unfocusedTextColor=Color.White, focusedBorderColor=Pink, unfocusedBorderColor=Muted, focusedLabelColor=Pink, unfocusedLabelColor=Muted))
                    Spacer(Modifier.height(12.dp))
                    Button(onClick={ result="Your request is ready. To generate real AI output, connect a secure backend and an AI provider key. This app does not pretend to generate results without that connection." }, modifier=Modifier.fillMaxWidth(), colors=ButtonDefaults.buttonColors(containerColor=Pink)) { Icon(Icons.Default.AutoAwesome, null); Spacer(Modifier.width(8.dp)); Text("Generate with AI") }
                    if (result.isNotBlank()) {
                        Spacer(Modifier.height(18.dp))
                        Text("Next step", color=Color.White, fontWeight=FontWeight.Bold)
                        Text(result, color=Muted, modifier=Modifier.padding(top=6.dp))
                    }
                }
                Spacer(Modifier.height(20.dp))
            }
            NavigationBar(containerColor=Color(0xFF0A1225), contentColor=Muted) {
                listOf("Home","Tools","Projects","Profile").forEachIndexed { i, name ->
                    NavigationBarItem(selected=tab==name, onClick={ tab=name; if(name=="Home" || name=="Tools") selected=null }, icon={ Icon(when(i){0->Icons.Default.Home;1->Icons.Default.GridView;2->Icons.Default.Folder;else->Icons.Default.Person}, null) }, label={Text(name)}, colors=NavigationBarItemDefaults.colors(selectedIconColor=Pink, selectedTextColor=Pink, indicatorColor=CardBg, unselectedIconColor=Muted, unselectedTextColor=Muted))
                }
            }
        }
    }
}
