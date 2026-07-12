// @ts-check
import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';
import cloudflare from '@astrojs/cloudflare';

// https://astro.build/config
export default defineConfig({
    integrations: [
        starlight({
            title: 'Minegun',
            social: [{ icon: 'github', label: 'GitHub', href: 'https://github.com/withastro/starlight' }],
            sidebar: [
                {
                    label: 'Guides',
                    items: [
                        { label: 'Getting Started', slug: 'guides/gettingstarted'},
                    ],
                },
                {
                    label: 'Classes',
                    items: [
                        { label: 'Raycast Weapons', slug: 'classes/raycastweapons'},
                    ],
                },
            ],
        }),
    ],

    // Configure Cloudflare to process images locally during build time
    adapter: cloudflare({
        imageService: 'compile',
    }),

    // Add this Vite block to bypass the SSR path resolution bug
    vite: {
        ssr: {
            external: ['@expressive-code/core', 'postcss', 'path', 'node:path'],
        },
    },
});