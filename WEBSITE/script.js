// Pixelz Client Website - Interactions
const $ = (s, root=document) => root.querySelector(s);
const $$ = (s, root=document) => [...root.querySelectorAll(s)];

const toastEl = $('#toast');
function toast(msg){
  if(!toastEl) return;
  toastEl.textContent = msg;
  toastEl.classList.add('show');
  clearTimeout(toastEl._t);
  toastEl._t = setTimeout(()=> toastEl.classList.remove('show'), 2800);
}

// Nav scroll
const nav = $('#nav');
addEventListener('scroll', () => {
  if(scrollY > 10) nav.classList.add('scrolled');
  else nav.classList.remove('scrolled');
}, {passive:true});

// Mobile menu
const ham = $('#hamburger');
const mobileMenu = $('#mobileMenu');
if(ham && mobileMenu){
  ham.addEventListener('click', () => mobileMenu.classList.toggle('open'));
  $$('a', mobileMenu).forEach(a => a.addEventListener('click', () => mobileMenu.classList.remove('open')));
}

// Modules filter + search
const chips = $$('.chip');
const cards = $$('.mod-card');
const search = $('#moduleSearch');
const countEl = $('#moduleCount');
let activeFilter = 'all';

function applyFilters(){
  const q = (search?.value || '').trim().toLowerCase();
  let shown = 0;
  cards.forEach(card => {
    const cat = card.dataset.cat;
    const text = card.textContent.toLowerCase();
    const catMatch = activeFilter === 'all' || cat === activeFilter;
    const qMatch = !q || text.includes(q);
    const visible = catMatch && qMatch;
    card.style.display = visible ? '' : 'none';
    if(visible) shown++;
  });
  if(countEl) countEl.textContent = `Showing ${shown} of ${cards.length} modules`;
}

chips.forEach(chip => {
  chip.addEventListener('click', () => {
    chips.forEach(c => c.classList.remove('active'));
    chip.classList.add('active');
    activeFilter = chip.dataset.filter;
    applyFilters();
  });
});
search?.addEventListener('input', applyFilters);

// FAQ accordion
$$('.faq-item').forEach(item => {
  const btn = $('.faq-q', item);
  btn?.addEventListener('click', () => {
    const wasOpen = item.classList.contains('open');
    $$('.faq-item').forEach(i => i.classList.remove('open'));
    if(!wasOpen) item.classList.add('open');
  });
});

// Copy buttons
$$('.copy-btn').forEach(btn => {
  btn.addEventListener('click', async () => {
    const text = btn.dataset.copy || btn.getAttribute('data-copy') || '';
    try{
      await navigator.clipboard.writeText(text.replaceAll('&#10;', '\n'));
      const orig = btn.textContent;
      btn.textContent = 'Copied!';
      toast('Copied to clipboard');
      setTimeout(()=> btn.textContent = orig, 1400);
    }catch{
      toast('Copy failed — select and copy manually');
    }
  });
});

// Download handling - tries to find real jar, else toast
async function handleDownload(e){
  e.preventDefault();
  // Try to fetch jar from build output if served locally
  const jarUrls = [
    '../build/libs/pixelz-client-1.1.0.jar',
    './pixelz-client-1.1.0.jar',
    '/build/libs/pixelz-client-1.1.0.jar'
  ];
  for(const url of jarUrls){
    try{
      const res = await fetch(url, {method:'HEAD'});
      if(res.ok){
        window.location.href = url;
        toast('Downloading pixelz-client-1.1.0.jar');
        return;
      }
    }catch{}
  }
  // Fallback: generate a tiny info file download
  const blob = new Blob([
`Pixelz Client 1.1.0 - 1.21.11 Fabric
Build: ./gradlew build
Jar: build/libs/pixelz-client-1.1.0.jar
Install: drop into mods/ with Fabric Loader 0.19.3 + Fabric API 0.141.6
Docs: see README.md and Pixelz_Client_Mods.txt
`
  ], {type:'text/plain'});
  const a = document.createElement('a');
  a.href = URL.createObjectURL(blob);
  a.download = 'Pixelz_Client_Info.txt';
  a.click();
  URL.revokeObjectURL(a.href);
  toast('Demo download — build the jar with ./gradlew build');
}

['#downloadBtn','#downloadBtn2','#downloadBtn3'].forEach(sel => {
  const el = $(sel);
  if(el) el.addEventListener('click', handleDownload);
});

// View license
$('#viewLicense')?.addEventListener('click', (e)=>{
  e.preventDefault();
  toast('GPL-3.0 — See LICENSE file in project root');
});

// Init
applyFilters();

// Easter egg: Konami-like pixelz
let keys = [];
addEventListener('keydown', (e)=>{
  keys.push(e.key.toLowerCase());
  if(keys.join('').includes('pixelz')){
    toast('◆ Pixelz Client — Stay violet. Stay fast.');
    keys = [];
  }
  if(keys.length>20) keys.shift();
});
